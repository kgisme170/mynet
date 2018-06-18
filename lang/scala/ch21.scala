val cont:(Unit=>Unit)=null
val filename="myfile.txt"
var contents=""
reset{
    while(contents==""){
        try{
            contents=scala.io.Source.fromFile(filename,"UTF-8").mkString
        }catch{case _=>}
        shift {k:(Unit=>Unit)=>cont=k}
    }
}
if(contents==""){
    print("Try another filename: ")
    filename=readLine()
    cont()
}
println(contents)