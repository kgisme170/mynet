#!/usr/bin/env kotlin
fun main(args: Array<String>) {
    println("Hello World!" + args[0])
}
println("hello")
var a1 = 1
val s = "string"
val temp:String = "finish"
a1 = 2
class Test {
    companion object {
        var Global = "hello"
    }
}
println(Test.Global)

val name get() = 2
println(name.javaClass)

fun add(x:Int, y:Int):Int{return x+y}
fun minus(x:Int, y:Int)=x-y
fun addPrint(x:Int, y:Int){println(x+y)}
fun toString(x:Int, y:Int):String?{
    if(x>y){return x.toString()}
    else{return null}
}
val a = "A"
val b = "B"
println("a的值是" + a + ",b的值是" + b)//传统写法
println("a的值是$a,b的值是$b")//Kotlin替代传统写法
println("a的值是${a.replace("A", "C")},b的值是$b")//Kotlin写法，在模板中写代码

open class TestA {
    init {}
    open fun testOpen() {
        println(this)
    }
}
class TestB : TestA() {
    override fun testOpen() {
        super.testOpen()
    }
}