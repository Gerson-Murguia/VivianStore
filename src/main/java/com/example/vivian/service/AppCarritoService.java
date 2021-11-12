package com.example.vivian.service;

import org.springframework.stereotype.Service;

import com.example.vivian.repository.AppCarritoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppCarritoService {
	private final AppCarritoRepository carritorepo;
}
