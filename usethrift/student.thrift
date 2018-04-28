struct Student{
 1:i32 no,
 2:string name,
 11:bool gendor,
 12:i16 age,
}
service Serv{
 i32 put(1:Student s),
}
