#!/usr/bin/python
# -*- coding: UTF-8 -*-

class matrix:
	''''计算乘方
	 base 底数
	 exponent 指数
	 '''
	def mathPower(base,exponent):
        	result = 1;
        	i=1;
        	while i <= exponent:
            		result *= base;
            		i=i+1;
        	return result;

        '''print matrix'''
        def printmatrixPrint(matrix):
        	'''for i in matrix:
        		if type(i) == 'list':
        			self.printmatrixPrint(i);
        	','.join(matrix).replace("[[","[\n [");'''
        	print matrix;


matrix = matrix();
