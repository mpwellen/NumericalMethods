# -*- coding: utf-8 -*-
"""
Created on Mon Apr  8 10:30:56 2019

@author: Michael
"""

def factorial(x):
    num=x
    answer=x
    while(num>1):
        num=num-1
        answer=answer*num
    return answer
      
file=open("mpw_HW4.txt","a")



print('There are ',factorial(52),' possible combinations of a deck of 52 cards')  
file.write("There are "+str(factorial(52))+" Possible combinations from a 52 card deck\n")

answer=factorial(52)/(factorial(47)*factorial(5))
print('There are ',answer,' possible hands that can made with a 52 card deck')
file.write("There are "+str(answer)+" Possible hands that can be made with a 52 card deck\n")

file.close()
