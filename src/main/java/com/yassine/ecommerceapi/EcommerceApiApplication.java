package com.yassine.ecommerceapi;

import com.yassine.ecommerceapi.Dto.ReviewDTO;
import com.yassine.ecommerceapi.Entity.*;
import com.yassine.ecommerceapi.enums.OrderStatus;
import com.yassine.ecommerceapi.enums.PaymentStatus;
import com.yassine.ecommerceapi.enums.Role;
import com.yassine.ecommerceapi.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class EcommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
    }
/*
    @Bean
    CommandLineRunner run(OrderRepository orderRepository,
                          OrderItemRepository orderItemRepository,
                          PaymentRepository paymentRepository,
                          ProductRepository productRepository,
                          UserRepository userRepository,
                          CategoryRepository categoryRepository,
                          CartRepository cartRepository,
                          ReviewRepository reviewRepository) {

        return args -> {

            // 1. Création d'utilisateurs
            User carlos = User.builder()
                    .firstName("Carlos")
                    .email("carlos.gasol@gmail.com")
                    .password("1234")
                    .role(Role.ADMIN)
                    .createdAt(LocalDateTime.now())
                    .build();

            User oumaima = User.builder()
                    .firstName("oumaima")
                    .email("oumaimabouchti@gmail.com")
                    .password("1234")
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();

            User karim = User.builder()
                    .firstName("Karim")
                    .email("karim.abc@gmail.com")
                    .password("1234")
                    .role(Role.USER)
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.saveAll(Arrays.asList(carlos, oumaima, karim));

            // 2. Création de catégories
            Category electronics = Category.builder().name("Electronics").build();
            Category fashion = Category.builder().name("Fashion").build();
            Category books = Category.builder().name("Books").build();

            categoryRepository.saveAll(Arrays.asList(electronics, fashion, books));

            // 3. Création de produits
            Product p1 = Product.builder()
                    .name("Smartphone Samsung Galaxy A54")
                    .description("Galaxy A54 128GB")
                    .price(4500)
                    .quantity(50)
                    .category(electronics)
                    .build();

            Product p2 = Product.builder()
                    .name("Casque JBL Bluetooth")
                    .description("JBL TUNE 510BT")
                    .price(600)
                    .quantity(100)
                    .category(electronics)
                    .build();

            Product p3 = Product.builder()
                    .name("T-shirt Nike Original")
                    .description("Black T-shirt Nike")
                    .price(100)
                    .quantity(200)
                    .category(fashion)
                    .build();

            Product p4 = Product.builder()
                    .name("Livre Java pour Débutants")
                    .description("Un guide complet pour apprendre Java")
                    .price(150)
                    .quantity(30)
                    .category(books)
                    .build();

            productRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

            // 4. Création de commandes et paiements
            Order order1 = Order.builder()
                    .customer(carlos)
                    .orderDate(LocalDateTime.now())
                    .status(OrderStatus.PENDING)
                    .build();
            orderRepository.save(order1);
            Order order2 = Order.builder()
                    .customer(carlos)
                    .orderDate(LocalDateTime.now())
                    .status(OrderStatus.PENDING)
                    .build();
            orderRepository.save(order2);
            Order order3 = Order.builder()
                    .customer(oumaima)
                    .orderDate(LocalDateTime.now())
                    .status(OrderStatus.PENDING)
                    .build();
            orderRepository.save(order2);
            OrderItem orderItem1 = OrderItem.builder()
                    .order(order1)
                    .product(p1)
                    .quantity(2)
                    .unitPrice(p1.getPrice())
                    .price(p1.getPrice() * 2)
                    .build();

            OrderItem orderItem2 = OrderItem.builder()
                    .order(order1)
                    .product(p2)
                    .quantity(1)
                    .unitPrice(p2.getPrice())
                    .price(p2.getPrice())
                    .build();
            OrderItem orderItem3 = OrderItem.builder()
                    .order(order2)
                    .product(p2)
                    .quantity(3)
                    .unitPrice(p3.getPrice())
                    .price(p3.getPrice()*3)
                    .build();

            orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2));

            Payment payment = Payment.builder()
                    .order(order1)
                    .amountPaid(orderItem1.getPrice() + orderItem2.getPrice())
                    .paymentMethod("Credit Card")
                    .paymentStatus(PaymentStatus.COMPLETED)
                    .build();
            paymentRepository.save(payment);

            // 5. Création de paniers
            Cart cartYassine = new Cart();
            cartYassine.setUser(carlos);

            CartItem cartItem1 = new CartItem();
            cartItem1.setCart(cartYassine);
            cartItem1.setProductId(p3.getId());
            cartItem1.setQuantity(3);
            cartItem1.setUnitPrice(p3.getPrice());
            cartItem1.setTotalPrice(p3.getPrice() * 3);

            cartYassine.getItems().add(cartItem1);

            cartYassine.setTotal(cartYassine.getItems().stream()
                    .mapToDouble(CartItem::getTotalPrice)
                    .sum());

            cartRepository.save(cartYassine);

            // 6. Création d'avis
            Review review1 = Review.builder()
                    .rating(5)
                    .comment("Produit exceptionnel !")
                    .product(p1)
                    .user(carlos)
                    .createdAt(LocalDateTime.now())
                    .build();

            Review review2 = Review.builder()
                    .rating(4)
                    .comment("Très bon produit, satisfait.")
                    .product(p1)
                    .user(oumaima)
                    .createdAt(LocalDateTime.now())
                    .build();

            Review review3 = Review.builder()
                    .rating(3)
                    .comment("Correct mais peut mieux faire.")
                    .product(p2)
                    .user(karim)
                    .createdAt(LocalDateTime.now())
                    .build();

            reviewRepository.saveAll(Arrays.asList(review1, review2, review3));

            Delivery delivery1= Delivery.builder()
                    .adresse("Lot essafa")
                    .dateLivraison(LocalDate.now())
                    .user(carlos)
                    .status("En cours")
                    .order(order1)
                    .build();
            System.out.println("Données initialisées avec succès !");
        };
    }*/
}
