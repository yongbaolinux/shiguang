#!/usr/bin/python
# -*- coding: UTF-8 -*-

#数学计算库
class Math:
	# 计算一个数列的逆序数
	# 12345计为全正序 逆序数为0
	# arr 待计算逆序数的数列
	def listInverseNum(self,list):
		inverseNum = 0;
		for i in range(len(list) -1):
			for j in range(i+1,len(list)):
				if(list[i] > list[j]):
					inverseNum += 1;
		return inverseNum;
	
#math  = Math();
#print math.listInverseNum([1,4,2,3,5]);


