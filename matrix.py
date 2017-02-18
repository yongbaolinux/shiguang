#!/usr/bin/python
# -*- coding: UTF-8 -*-

from math import Math
# 矩阵计算库
class Matrix:
	#''''计算乘方  base 底数 exponent 指数'''
	def mathPower(base,exponent):
        	result = 1;
        	i=1;
        	while i <= exponent:
            		result *= base;
            		i=i+1;
        	return result;

        #'''打印矩阵'''
        def matrixPrint(self,matrix):
        	print '['
        	for i in matrix:
        		if type(i) == "<type 'list'>":
        			self.matrixPrint(i);
        		print i;
        	print ']'

        #'''去除一维列表中的重复值'''
        def  array_unique(self,array):
        	array_ = [];
        	for i in array:
        		if  i not in array_:
        			array_.append(i);
        	return array_;

       	#'''去除多维列表中的重复值'''
       	def multi_array_unique(self,array):
       		array_ = self.array_unique(array);
       		for i in range(len(array_)):
       			if isinstance(array_[i],(list)) == True:
       				array_[i] = self.multi_array_unique(array_[i]);
       		array__ = self.array_unique(array_);
       		return array__;

       	#'''验证输入的python列表是否能表示一个合法的矩阵 验证成功返回一个由行数和列数组成的两个元素的一维python列 表 验证失败返回false'''
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
	#'矩阵的和'
	def matrixPlus(self,matrix1,matrix2):
		matrix1_info = self.validMatrix(matrix1) ;
		matrix2_info = self.validMatrix(matrix2) ;
		if (matrix1_info  == False) or (matrix1_info == False):
			print 'paramters is invalid';
			return False;
		if(matrix1_info[0] != matrix2_info[0]) or (matrix1_info[1] != matrix2_info[1]):
			print 'can\'t plus';
			return False;
		matrix3 = matrix1;
		for i in range(matrix1_info[0]):
			for j in range(matrix1_info[1]):
				matrix3[i][j] = int(matrix1[i][j]) + int(matrix2[i][j]);
		return matrix3;

	#'矩阵的差'
	def matrixMinus(self,matrix1,matrix2):
		matrix1_info = self.validMatrix(matrix1) ;
		matrix2_info = self.validMatrix(matrix2) ;
		if (matrix1_info  == False) or (matrix1_info == False):
			print 'paramters is invalid';
			return False;
		if(matrix1_info[0] != matrix2_info[0]) or (matrix1_info[1] != matrix2_info[1]):
			print 'can\'t minus';
			return False;
		matrix3 = matrix1;
		for i in range(matrix1_info[0]):
			for j in range(matrix1_info[1]):
				matrix3[i][j] = int(matrix1[i][j]) - int(matrix2[i][j]);
		return matrix3;

	#'矩阵的积'
	def matrixMulti(self,matrix1,matrix2):
		matrix1_info = self.validMatrix(matrix1) ;
		matrix2_info = self.validMatrix(matrix2) ;
		if (matrix1_info  == False) or (matrix1_info == False):
			print 'paramters is invalid';
			return False;
		if matrix1_info[1] != matrix2_info[0]:
			print 'can\'t multi';
			return False;
		#matrix3 = [[0]*matrix2_info[1]]*matrix1_info[0];
		matrix3 = [[0 for i in range(matrix2_info[1])] for i in range(matrix1_info[0])];
		for i in range(matrix1_info[0]):
			for j in range(matrix2_info[1]):
				#temp = 0;
				for k in range(matrix1_info[1]):
					matrix3[i][j]  += int(matrix1[i][k]) * int(matrix2[k][j]);
				#matrix3[i][j] = temp;
				#print temp;
		return matrix3;

	#'矩阵反转 行和列的元素互换'
	def matrixReverse(self,matrix):
		matrix_info = self.validMatrix(matrix);
		if matrix_info == False:
			print 'paramters is invalid';
			return False;
		matrix_  = [[0 for i in range(matrix_info[0])] for i in range(matrix_info[1])]
		for i in range(matrix_info[1]):
			for j in range(matrix_info[0]):
				matrix_[i][j] = matrix[j][i];
		return matrix_;

#matrix = Matrix();
#result = matrix.matrixMulti([['1','2'],['3','4']],[['1','2'],['3','4']]);
#matrix_.matrixPrint([['a','b','c'],['a','b','c'],['a','b','c']]);
#print matrix.multi_array_unique([['a','b','b','c'],['a','b','c','c']]);
#print matrix.validMatrix([['a','b'],['b','c']]);
#list = ['a','b','c'];
#print ','.join(list);
#result = matrix.matrixReverse([['1','2'],[3,4],[5,6]]);

#matrix.matrixPrint(result);
math  = Math();
print math.listInverseNum([1,4,2,3,5]);