package com.kjs.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kjs.Entity.Book;
import com.kjs.Entity.OrderItem;
import com.kjs.Entity.OrderStatus;
import com.kjs.Entity.Orders;
import com.kjs.Entity.User;
import com.kjs.dto.OrderItemRequest;
import com.kjs.dto.OrderRequest;
import com.kjs.exception.InsufficientStockException;
import com.kjs.exception.OrderNotFoundException;
import com.kjs.repository.BookRepository;
import com.kjs.repository.OrderItemRepository;
import com.kjs.repository.OrderRepository;
import com.kjs.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
	
	private OrderRepository ordersRepo;
	private UserRepository userRepo;
	private BookRepository bookRepo;
	private OrderItemRepository orderItemRepo;
	
	@Transactional
	public Orders placeOrder(OrderRequest request)
	{
		User user = userRepo.findById(request.getUserId()).get();
		
		double totalAmt = 0;
		double discount = 0;
		
		   List<Book> books = new ArrayList<>();
		
		for (OrderItemRequest item : request.getItems()) {

			Book book = bookRepo.findById(item.getBookId()).get();
			
			if (book.getStock() < item.getQuantity()) {
			    throw new InsufficientStockException("Insufficient stock");
			}
			
			totalAmt = totalAmt +  book.getPrice() * item.getQuantity();
			
			books.add(book);
				        
		}
		
		if(totalAmt > 1000)
		{
			discount = totalAmt * 0.1;
		}
		
		double taxAmt = totalAmt - discount;
		double gst = taxAmt * 0.05;
		double finalAmt = taxAmt + gst;
		
		Orders order = new Orders();

		order.setUser(user);
		order.setTotalAmount(finalAmt);
		order.setStatus(OrderStatus.PLACED);
		order.setCreatedAt(LocalDateTime.now());
		ordersRepo.save(order);
		
		List<OrderItem> orderItems = new ArrayList();
		
		for (int i =0; i<request.getItems().size() ; i++)
		{
			OrderItemRequest item = request.getItems().get(i);
			Book b = books.get(i);
			
			OrderItem oi =  new OrderItem();
			
			oi.setOrder(order);
			oi.setBook(b);
			oi.setQuantity(item.getQuantity());
			oi.setPriceAtPurchase(b.getPrice());
			
			orderItems.add(oi);
			
			
			b.setStock(b.getStock() - item.getQuantity());
			
			bookRepo.save(b);
			
		}
		
		orderItemRepo.saveAll(orderItems);
		return order;
		
		
	}

	@Transactional
	public Orders cancelOrder(Long id) {
		
		Orders oi = ordersRepo.findById(id).orElseThrow(()->  new OrderNotFoundException("Entered Order ID is not Found"));
	
		if(oi.getStatus() == OrderStatus.PLACED)
		{
		List<OrderItem> or = oi.getItems();
		for(OrderItem item : or)
		{
			Book b = item.getBook();
			b.setStock(b.getStock() + item.getQuantity());
			bookRepo.save(b);
		}
		
			oi.setStatus(OrderStatus.CANCELLED);
			ordersRepo.save(oi);
		}else {
	        throw new RuntimeException("Order is already cancelled");
	    }
		return oi;
	}

	public List<Orders> getAllOrders() {
		
		return ordersRepo.findAll();
	}
	
	public Page<Orders> getOrdersByUserId(Long userId, int page, int size) {

	    Pageable pageable = PageRequest.of(page, size);

	    return ordersRepo.findByUserId(userId, pageable);
	}
	
	
}
