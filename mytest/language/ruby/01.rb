#!/usr/bin/env ruby   
# filename : 01.rb

class Sample
    def hello
        puts "Hello, World!"
    end
end

s = Sample.new
s.hello

a = 250
s = "yes"
b = 1+2
c = 0.0001
modifier = "very "
mood = "excited"
puts "I am #{modifier * 3 + mood} for today's play!"

for i in 0..5
    puts "局部变量的值为 #{i}"
end

5.times do
    puts "Hello, World!"
end
5.times do |i|
    puts "#{i}: Hello, World!"
end
5.times{ puts "Hello, World!" }
"this is a sentence".gsub("e"){ puts "Found an E!"}

meals = ["Breakfast", "Lunch", "Dinner"]
meals << "Dessert"
puts meals.last
array1 = ["this", "is", "an", "array"]
array1.sort
puts array1

produce = {"apples" => 3, "oranges" => 1, "carrots" => 12}
puts produce.values

produce = {apples: 3, oranges: 1, carrots: 12}
puts "There are #{produce[:oranges]} oranges in the fridge."

class Student
    attr_accessor :first_name, :last_name, :primary_phone_number
  
    #def introduction
    #    puts "Hi, I'm #{first_name}!"
    #end

    def introduction(target)
        puts "Hi #{target}, I'm #{first_name}!"
    end
    def favorite_number
        5
    end
end

frank = Student.new
frank.first_name = "Frank"
#frank.introduction
frank.introduction('Minlee')
puts "Maxsu's favorite number is #{frank.favorite_number}."