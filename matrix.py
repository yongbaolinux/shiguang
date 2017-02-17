#!/usr/bin/python
# -*- coding: UTF-8 -*-

class matrix:
	''''计算乘方  base 底数 exponent 指数'''
	def mathPower(base,exponent):
        	result = 1;
        	i=1;
        	while i <= exponent:
            		result *= base;
            		i=i+1;
        	return result;

        '''print matrix'''
        def matrixPrint(self,matrix):
        	print '['
        	for i in matrix:
        		if type(i) == "<type 'list'>":
        			self.matrixPrint(i);
        		print i;
        	print ']'

        '''去除一维列表中的重复值'''
        def  array_unique(self,array):
        	array_ = [];
        	for i in array:
        		if  i not in array_:
        			array_.append(i);
        	return array_;

       	'''去除多维列表中的重复值'''
       	def multi_array_unique(self,array):
       		array_ = self.array_unique(array);
       		for i in range(len(array_)):
       			if isinstance(array_[i],(list)) == True:
       				array_[i] = self.multi_array_unique(array_[i]);
       		array__ = self.array_unique(array_);
       		return array__;

       	'''验证输入的python列表是否能表示一个合法的矩阵 验证成功返回一个由行数和列数组成的两个元素的一维python列 表 验证失败返回false'''
       	def validMatrix(slef,matrix):
       		if isinstance(matrix,(list)) == False:
       			return False;
       		length = len(matrix);
       		if length <= 0:
       			return False;
       		for i in range(length):
       			if isinstance(matrix[i],(list)) == False:
       				return False;
       			if i > 0:
				if len(matrix[i]) != len(matrix[i-1]):
					return False;
		return [length,len(matrix[0])];
	
matrix = matrix()
#matrix_.matrixPrint([['a','b','c'],['a','b','c'],['a','b','c']]);
#print matrix.multi_array_unique([['a','b','b','c'],['a','b','c','c']]);
print matrix.validMatrix([['a','b'],['b','c']]);
#list = ['a','b','c'];
#print ','.join(list);
