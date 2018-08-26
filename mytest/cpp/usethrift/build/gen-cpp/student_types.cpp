/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#include "student_types.h"

#include <algorithm>
#include <ostream>

#include <thrift/TToString.h>




Student::~Student() throw() {
}


void Student::__set_no(const int32_t val) {
  this->no = val;
}

void Student::__set_name(const std::string& val) {
  this->name = val;
}

void Student::__set_gendor(const bool val) {
  this->gendor = val;
}

void Student::__set_age(const int16_t val) {
  this->age = val;
}

uint32_t Student::read(::apache::thrift::protocol::TProtocol* iprot) {

  apache::thrift::protocol::TInputRecursionTracker tracker(*iprot);
  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          xfer += iprot->readI32(this->no);
          this->__isset.no = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->name);
          this->__isset.name = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 11:
        if (ftype == ::apache::thrift::protocol::T_BOOL) {
          xfer += iprot->readBool(this->gendor);
          this->__isset.gendor = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 12:
        if (ftype == ::apache::thrift::protocol::T_I16) {
          xfer += iprot->readI16(this->age);
          this->__isset.age = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t Student::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  apache::thrift::protocol::TOutputRecursionTracker tracker(*oprot);
  xfer += oprot->writeStructBegin("Student");

  xfer += oprot->writeFieldBegin("no", ::apache::thrift::protocol::T_I32, 1);
  xfer += oprot->writeI32(this->no);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("name", ::apache::thrift::protocol::T_STRING, 2);
  xfer += oprot->writeString(this->name);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("gendor", ::apache::thrift::protocol::T_BOOL, 11);
  xfer += oprot->writeBool(this->gendor);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("age", ::apache::thrift::protocol::T_I16, 12);
  xfer += oprot->writeI16(this->age);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(Student &a, Student &b) {
  using ::std::swap;
  swap(a.no, b.no);
  swap(a.name, b.name);
  swap(a.gendor, b.gendor);
  swap(a.age, b.age);
  swap(a.__isset, b.__isset);
}

Student::Student(const Student& other0) {
  no = other0.no;
  name = other0.name;
  gendor = other0.gendor;
  age = other0.age;
  __isset = other0.__isset;
}
Student& Student::operator=(const Student& other1) {
  no = other1.no;
  name = other1.name;
  gendor = other1.gendor;
  age = other1.age;
  __isset = other1.__isset;
  return *this;
}
void Student::printTo(std::ostream& out) const {
  using ::apache::thrift::to_string;
  out << "Student(";
  out << "no=" << to_string(no);
  out << ", " << "name=" << to_string(name);
  out << ", " << "gendor=" << to_string(gendor);
  out << ", " << "age=" << to_string(age);
  out << ")";
}


