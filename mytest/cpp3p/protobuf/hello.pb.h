// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: hello.proto

#ifndef PROTOBUF_INCLUDED_hello_2eproto
#define PROTOBUF_INCLUDED_hello_2eproto

#include <limits>
#include <string>

#include <google/protobuf/port_def.inc>
#if PROTOBUF_VERSION < 3006001
#error This file was generated by a newer version of protoc which is
#error incompatible with your Protocol Buffer headers. Please update
#error your headers.
#endif
#if 3006001 < PROTOBUF_MIN_PROTOC_VERSION
#error This file was generated by an older version of protoc which is
#error incompatible with your Protocol Buffer headers. Please
#error regenerate this file with a newer version of protoc.
#endif

#include <google/protobuf/port_undef.inc>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/arena.h>
#include <google/protobuf/arenastring.h>
#include <google/protobuf/generated_message_table_driven.h>
#include <google/protobuf/generated_message_util.h>
#include <google/protobuf/inlined_string_field.h>
#include <google/protobuf/metadata.h>
#include <google/protobuf/message.h>
#include <google/protobuf/repeated_field.h>  // IWYU pragma: export
#include <google/protobuf/extension_set.h>  // IWYU pragma: export
#include <google/protobuf/unknown_field_set.h>
// @@protoc_insertion_point(includes)
#include <google/protobuf/port_def.inc>
#define PROTOBUF_INTERNAL_EXPORT_hello_2eproto

// Internal implementation detail -- do not use these members.
struct TableStruct_hello_2eproto {
  static const ::google::protobuf::internal::ParseTableField entries[]
    PROTOBUF_SECTION_VARIABLE(protodesc_cold);
  static const ::google::protobuf::internal::AuxillaryParseTableField aux[]
    PROTOBUF_SECTION_VARIABLE(protodesc_cold);
  static const ::google::protobuf::internal::ParseTable schema[1]
    PROTOBUF_SECTION_VARIABLE(protodesc_cold);
  static const ::google::protobuf::internal::FieldMetadata field_metadata[];
  static const ::google::protobuf::internal::SerializationTable serialization_table[];
  static const ::google::protobuf::uint32 offsets[];
};
void AddDescriptors_hello_2eproto();
class hello;
class helloDefaultTypeInternal;
extern helloDefaultTypeInternal _hello_default_instance_;
namespace google {
namespace protobuf {
template<> ::hello* Arena::CreateMaybeMessage<::hello>(Arena*);
}  // namespace protobuf
}  // namespace google

// ===================================================================

class hello final :
    public ::google::protobuf::Message /* @@protoc_insertion_point(class_definition:hello) */ {
 public:
  hello();
  virtual ~hello();

  hello(const hello& from);

  inline hello& operator=(const hello& from) {
    CopyFrom(from);
    return *this;
  }
  #if LANG_CXX11
  hello(hello&& from) noexcept
    : hello() {
    *this = ::std::move(from);
  }

  inline hello& operator=(hello&& from) noexcept {
    if (GetArenaNoVirtual() == from.GetArenaNoVirtual()) {
      if (this != &from) InternalSwap(&from);
    } else {
      CopyFrom(from);
    }
    return *this;
  }
  #endif
  inline const ::google::protobuf::UnknownFieldSet& unknown_fields() const {
    return _internal_metadata_.unknown_fields();
  }
  inline ::google::protobuf::UnknownFieldSet* mutable_unknown_fields() {
    return _internal_metadata_.mutable_unknown_fields();
  }

  static const ::google::protobuf::Descriptor* descriptor() {
    return default_instance().GetDescriptor();
  }
  static const hello& default_instance();

  static void InitAsDefaultInstance();  // FOR INTERNAL USE ONLY
  static inline const hello* internal_default_instance() {
    return reinterpret_cast<const hello*>(
               &_hello_default_instance_);
  }
  static constexpr int kIndexInFileMessages =
    0;

  void Swap(hello* other);
  friend void swap(hello& a, hello& b) {
    a.Swap(&b);
  }

  // implements Message ----------------------------------------------

  inline hello* New() const final {
    return CreateMaybeMessage<hello>(nullptr);
  }

  hello* New(::google::protobuf::Arena* arena) const final {
    return CreateMaybeMessage<hello>(arena);
  }
  void CopyFrom(const ::google::protobuf::Message& from) final;
  void MergeFrom(const ::google::protobuf::Message& from) final;
  void CopyFrom(const hello& from);
  void MergeFrom(const hello& from);
  PROTOBUF_ATTRIBUTE_REINITIALIZES void Clear() final;
  bool IsInitialized() const final;

  size_t ByteSizeLong() const final;
  #if GOOGLE_PROTOBUF_ENABLE_EXPERIMENTAL_PARSER
  static const char* _InternalParse(const char* begin, const char* end, void* object, ::google::protobuf::internal::ParseContext* ctx);
  ::google::protobuf::internal::ParseFunc _ParseFunc() const final { return _InternalParse; }
  #else
  bool MergePartialFromCodedStream(
      ::google::protobuf::io::CodedInputStream* input) final;
  #endif  // GOOGLE_PROTOBUF_ENABLE_EXPERIMENTAL_PARSER
  void SerializeWithCachedSizes(
      ::google::protobuf::io::CodedOutputStream* output) const final;
  ::google::protobuf::uint8* InternalSerializeWithCachedSizesToArray(
      ::google::protobuf::uint8* target) const final;
  int GetCachedSize() const final { return _cached_size_.Get(); }

  private:
  void SharedCtor();
  void SharedDtor();
  void SetCachedSize(int size) const final;
  void InternalSwap(hello* other);
  private:
  inline ::google::protobuf::Arena* GetArenaNoVirtual() const {
    return nullptr;
  }
  inline void* MaybeArenaPtr() const {
    return nullptr;
  }
  public:

  ::google::protobuf::Metadata GetMetadata() const final;

  // nested types ----------------------------------------------------

  // accessors -------------------------------------------------------

  // required int32 f1 = 1;
  bool has_f1() const;
  void clear_f1();
  static const int kF1FieldNumber = 1;
  ::google::protobuf::int32 f1() const;
  void set_f1(::google::protobuf::int32 value);

  // required int32 f2 = 2;
  bool has_f2() const;
  void clear_f2();
  static const int kF2FieldNumber = 2;
  ::google::protobuf::int32 f2() const;
  void set_f2(::google::protobuf::int32 value);

  // optional int32 f3 = 3;
  bool has_f3() const;
  void clear_f3();
  static const int kF3FieldNumber = 3;
  ::google::protobuf::int32 f3() const;
  void set_f3(::google::protobuf::int32 value);

  // @@protoc_insertion_point(class_scope:hello)
 private:
  class HasBitSetters;

  // helper for ByteSizeLong()
  size_t RequiredFieldsByteSizeFallback() const;

  ::google::protobuf::internal::InternalMetadataWithArena _internal_metadata_;
  ::google::protobuf::internal::HasBits<1> _has_bits_;
  mutable ::google::protobuf::internal::CachedSize _cached_size_;
  ::google::protobuf::int32 f1_;
  ::google::protobuf::int32 f2_;
  ::google::protobuf::int32 f3_;
  friend struct ::TableStruct_hello_2eproto;
};
// ===================================================================


// ===================================================================

#ifdef __GNUC__
  #pragma GCC diagnostic push
  #pragma GCC diagnostic ignored "-Wstrict-aliasing"
#endif  // __GNUC__
// hello

// required int32 f1 = 1;
inline bool hello::has_f1() const {
  return (_has_bits_[0] & 0x00000001u) != 0;
}
inline void hello::clear_f1() {
  f1_ = 0;
  _has_bits_[0] &= ~0x00000001u;
}
inline ::google::protobuf::int32 hello::f1() const {
  // @@protoc_insertion_point(field_get:hello.f1)
  return f1_;
}
inline void hello::set_f1(::google::protobuf::int32 value) {
  _has_bits_[0] |= 0x00000001u;
  f1_ = value;
  // @@protoc_insertion_point(field_set:hello.f1)
}

// required int32 f2 = 2;
inline bool hello::has_f2() const {
  return (_has_bits_[0] & 0x00000002u) != 0;
}
inline void hello::clear_f2() {
  f2_ = 0;
  _has_bits_[0] &= ~0x00000002u;
}
inline ::google::protobuf::int32 hello::f2() const {
  // @@protoc_insertion_point(field_get:hello.f2)
  return f2_;
}
inline void hello::set_f2(::google::protobuf::int32 value) {
  _has_bits_[0] |= 0x00000002u;
  f2_ = value;
  // @@protoc_insertion_point(field_set:hello.f2)
}

// optional int32 f3 = 3;
inline bool hello::has_f3() const {
  return (_has_bits_[0] & 0x00000004u) != 0;
}
inline void hello::clear_f3() {
  f3_ = 0;
  _has_bits_[0] &= ~0x00000004u;
}
inline ::google::protobuf::int32 hello::f3() const {
  // @@protoc_insertion_point(field_get:hello.f3)
  return f3_;
}
inline void hello::set_f3(::google::protobuf::int32 value) {
  _has_bits_[0] |= 0x00000004u;
  f3_ = value;
  // @@protoc_insertion_point(field_set:hello.f3)
}

#ifdef __GNUC__
  #pragma GCC diagnostic pop
#endif  // __GNUC__

// @@protoc_insertion_point(namespace_scope)


// @@protoc_insertion_point(global_scope)

#include <google/protobuf/port_undef.inc>
#endif  // PROTOBUF_INCLUDED_hello_2eproto
