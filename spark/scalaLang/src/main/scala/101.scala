val vb : Byte = '1'
val vc : Char = 2
val vs : Short = 3
val vi : Int = 4
val vl : Long = 5
val vf : Float = 6
val vd : Double = 7
val vB : Boolean = true

var vc2 = 'a'
vc2 = 'b'
val vs2 = "abc"
val vm = if (vs==5)10 else 20
println(vm)

for(i <- 1 to 20 if i%2==0;if i!=2)print(i)
println
for(i <- 1 to 20;j<- i to 20) {
    print(i*j)
    print(',')
}