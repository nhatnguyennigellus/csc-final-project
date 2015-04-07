package csc.fresher.finalproject.controller;

import org.springframework.stereotype.Controller;

import csc.fresher.finalproject.service.TransactionService;

@Controller
public class TransactionController {
	private TransactionService transactionService = new TransactionService();
}
