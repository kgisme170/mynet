// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: serialize.proto

#include "serialize.pb.h"

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/extension_set.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)
#include <google/protobuf/port_def.inc>

namespace lm {
class helloDefaultTypeInternal {
 public:
  ::google::protobuf::internal::ExplicitlyConstructed<hello> _instance;
} _hello_default_instance_;
}  // namespace lm
static void InitDefaultshello_serialize_2eproto() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::lm::hello::_i_give_permission_to_break_this_code_default_str_.DefaultConstruct();
  *::lm::hello::_i_give_permission_to_break_this_code_default_str_.get_mutable() = ::std::string("abc", 3);
  ::google::protobuf::internal::OnShutdownDestroyString(
      ::lm::hello::_i_give_permission_to_break_this_code_default_str_.get_mutable());
  {
    void* ptr = &::lm::_hello_default_instance_;
    new (ptr) ::lm::hello();
    ::google::protobuf::internal::OnShutdownDestroyMessage(ptr);
  }
  ::lm::hello::InitAsDefaultInstance();
}

::google::protobuf::internal::SCCInfo<0> scc_info_hello_serialize_2eproto =
    {{ATOMIC_VAR_INIT(::google::protobuf::internal::SCCInfoBase::kUninitialized), 0, InitDefaultshello_serialize_2eproto}, {}};

void InitDefaults_serialize_2eproto() {
  ::google::protobuf::internal::InitSCC(&scc_info_hello_serialize_2eproto.base);
}

::google::protobuf::Metadata file_level_metadata_serialize_2eproto[1];
constexpr ::google::protobuf::EnumDescriptor const** file_level_enum_descriptors_serialize_2eproto = nullptr;
constexpr ::google::protobuf::ServiceDescriptor const** file_level_service_descriptors_serialize_2eproto = nullptr;

const ::google::protobuf::uint32 TableStruct_serialize_2eproto::offsets[] PROTOBUF_SECTION_VARIABLE(protodesc_cold) = {
  PROTOBUF_FIELD_OFFSET(::lm::hello, _has_bits_),
  PROTOBUF_FIELD_OFFSET(::lm::hello, _internal_metadata_),
  ~0u,  // no _extensions_
  ~0u,  // no _oneof_case_
  ~0u,  // no _weak_field_map_
  PROTOBUF_FIELD_OFFSET(::lm::hello, id_),
  PROTOBUF_FIELD_OFFSET(::lm::hello, str_),
  PROTOBUF_FIELD_OFFSET(::lm::hello, op_),
  1,
  0,
  2,
};
static const ::google::protobuf::internal::MigrationSchema schemas[] PROTOBUF_SECTION_VARIABLE(protodesc_cold) = {
  { 0, 8, sizeof(::lm::hello)},
};

static ::google::protobuf::Message const * const file_default_instances[] = {
  reinterpret_cast<const ::google::protobuf::Message*>(&::lm::_hello_default_instance_),
};

::google::protobuf::internal::AssignDescriptorsTable assign_descriptors_table_serialize_2eproto = {
  {}, AddDescriptors_serialize_2eproto, "serialize.proto", schemas,
  file_default_instances, TableStruct_serialize_2eproto::offsets,
  file_level_metadata_serialize_2eproto, 1, file_level_enum_descriptors_serialize_2eproto, file_level_service_descriptors_serialize_2eproto,
};

const char descriptor_table_protodef_serialize_2eproto[] =
  "\n\017serialize.proto\022\002lm\"5\n\005hello\022\n\n\002id\030\001 \001"
  "(\005\022\020\n\003str\030\002 \002(\t:\003abc\022\016\n\002op\030\003 \001(\005:\00215"
  ;
::google::protobuf::internal::DescriptorTable descriptor_table_serialize_2eproto = {
  false, InitDefaults_serialize_2eproto, 
  descriptor_table_protodef_serialize_2eproto,
  "serialize.proto", &assign_descriptors_table_serialize_2eproto, 76,
};

void AddDescriptors_serialize_2eproto() {
  static constexpr ::google::protobuf::internal::InitFunc deps[1] =
  {
  };
 ::google::protobuf::internal::AddDescriptors(&descriptor_table_serialize_2eproto, deps, 0);
}

// Force running AddDescriptors() at dynamic initialization time.
static bool dynamic_init_dummy_serialize_2eproto = []() { AddDescriptors_serialize_2eproto(); return true; }();
namespace lm {

// ===================================================================

void hello::InitAsDefaultInstance() {
}
class hello::HasBitSetters {
 public:
  static void set_has_id(hello* msg) {
    msg->_has_bits_[0] |= 0x00000002u;
  }
  static void set_has_str(hello* msg) {
    msg->_has_bits_[0] |= 0x00000001u;
  }
  static void set_has_op(hello* msg) {
    msg->_has_bits_[0] |= 0x00000004u;
  }
};

::google::protobuf::internal::ExplicitlyConstructed<::std::string> hello::_i_give_permission_to_break_this_code_default_str_;
#if !defined(_MSC_VER) || _MSC_VER >= 1900
const int hello::kIdFieldNumber;
const int hello::kStrFieldNumber;
const int hello::kOpFieldNumber;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

hello::hello()
  : ::google::protobuf::Message(), _internal_metadata_(nullptr) {
  SharedCtor();
  // @@protoc_insertion_point(constructor:lm.hello)
}
hello::hello(const hello& from)
  : ::google::protobuf::Message(),
      _internal_metadata_(nullptr),
      _has_bits_(from._has_bits_) {
  _internal_metadata_.MergeFrom(from._internal_metadata_);
  str_.UnsafeSetDefault(&::lm::hello::_i_give_permission_to_break_this_code_default_str_.get());
  if (from.has_str()) {
    str_.AssignWithDefault(&::lm::hello::_i_give_permission_to_break_this_code_default_str_.get(), from.str_);
  }
  ::memcpy(&id_, &from.id_,
    static_cast<size_t>(reinterpret_cast<char*>(&op_) -
    reinterpret_cast<char*>(&id_)) + sizeof(op_));
  // @@protoc_insertion_point(copy_constructor:lm.hello)
}

void hello::SharedCtor() {
  ::google::protobuf::internal::InitSCC(
      &scc_info_hello_serialize_2eproto.base);
  str_.UnsafeSetDefault(&::lm::hello::_i_give_permission_to_break_this_code_default_str_.get());
  id_ = 0;
  op_ = 15;
}

hello::~hello() {
  // @@protoc_insertion_point(destructor:lm.hello)
  SharedDtor();
}

void hello::SharedDtor() {
  str_.DestroyNoArena(&::lm::hello::_i_give_permission_to_break_this_code_default_str_.get());
}

void hello::SetCachedSize(int size) const {
  _cached_size_.Set(size);
}
const hello& hello::default_instance() {
  ::google::protobuf::internal::InitSCC(&::scc_info_hello_serialize_2eproto.base);
  return *internal_default_instance();
}


void hello::Clear() {
// @@protoc_insertion_point(message_clear_start:lm.hello)
  ::google::protobuf::uint32 cached_has_bits = 0;
  // Prevent compiler warnings about cached_has_bits being unused
  (void) cached_has_bits;

  cached_has_bits = _has_bits_[0];
  if (cached_has_bits & 0x00000001u) {
    str_.UnsafeMutablePointer()->assign(*&::lm::hello::_i_give_permission_to_break_this_code_default_str_.get());
  }
  if (cached_has_bits & 0x00000006u) {
    id_ = 0;
    op_ = 15;
  }
  _has_bits_.Clear();
  _internal_metadata_.Clear();
}

#if GOOGLE_PROTOBUF_ENABLE_EXPERIMENTAL_PARSER
const char* hello::_InternalParse(const char* begin, const char* end, void* object,
                  ::google::protobuf::internal::ParseContext* ctx) {
  auto msg = static_cast<hello*>(object);
  ::google::protobuf::int32 size; (void)size;
  int depth; (void)depth;
  ::google::protobuf::uint32 tag;
  ::google::protobuf::internal::ParseFunc parser_till_end; (void)parser_till_end;
  auto ptr = begin;
  while (ptr < end) {
    ptr = ::google::protobuf::io::Parse32(ptr, &tag);
    GOOGLE_PROTOBUF_PARSER_ASSERT(ptr);
    switch (tag >> 3) {
      // optional int32 id = 1;
      case 1: {
        if (static_cast<::google::protobuf::uint8>(tag) != 8) goto handle_unusual;
        msg->set_id(::google::protobuf::internal::ReadVarint(&ptr));
        GOOGLE_PROTOBUF_PARSER_ASSERT(ptr);
        break;
      }
      // required string str = 2 [default = "abc"];
      case 2: {
        if (static_cast<::google::protobuf::uint8>(tag) != 18) goto handle_unusual;
        ptr = ::google::protobuf::io::ReadSize(ptr, &size);
        GOOGLE_PROTOBUF_PARSER_ASSERT(ptr);
        ctx->extra_parse_data().SetFieldName("lm.hello.str");
        object = msg->mutable_str();
        if (size > end - ptr + ::google::protobuf::internal::ParseContext::kSlopBytes) {
          parser_till_end = ::google::protobuf::internal::GreedyStringParserUTF8Verify;
          goto string_till_end;
        }
        GOOGLE_PROTOBUF_PARSER_ASSERT(::google::protobuf::internal::StringCheckUTF8Verify(ptr, size, ctx));
        ::google::protobuf::internal::InlineGreedyStringParser(object, ptr, size, ctx);
        ptr += size;
        break;
      }
      // optional int32 op = 3 [default = 15];
      case 3: {
        if (static_cast<::google::protobuf::uint8>(tag) != 24) goto handle_unusual;
        msg->set_op(::google::protobuf::internal::ReadVarint(&ptr));
        GOOGLE_PROTOBUF_PARSER_ASSERT(ptr);
        break;
      }
      default: {
      handle_unusual:
        if ((tag & 7) == 4 || tag == 0) {
          ctx->EndGroup(tag);
          return ptr;
        }
        auto res = UnknownFieldParse(tag, {_InternalParse, msg},
          ptr, end, msg->_internal_metadata_.mutable_unknown_fields(), ctx);
        ptr = res.first;
        GOOGLE_PROTOBUF_PARSER_ASSERT(ptr != nullptr);
        if (res.second) return ptr;
      }
    }  // switch
  }  // while
  return ptr;
string_till_end:
  static_cast<::std::string*>(object)->clear();
  static_cast<::std::string*>(object)->reserve(size);
  goto len_delim_till_end;
len_delim_till_end:
  return ctx->StoreAndTailCall(ptr, end, {_InternalParse, msg},
                               {parser_till_end, object}, size);
}
#else  // GOOGLE_PROTOBUF_ENABLE_EXPERIMENTAL_PARSER
bool hello::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!PROTOBUF_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:lm.hello)
  for (;;) {
    ::std::pair<::google::protobuf::uint32, bool> p = input->ReadTagWithCutoffNoLastTag(127u);
    tag = p.first;
    if (!p.second) goto handle_unusual;
    switch (::google::protobuf::internal::WireFormatLite::GetTagFieldNumber(tag)) {
      // optional int32 id = 1;
      case 1: {
        if (static_cast< ::google::protobuf::uint8>(tag) == (8 & 0xFF)) {
          HasBitSetters::set_has_id(this);
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &id_)));
        } else {
          goto handle_unusual;
        }
        break;
      }

      // required string str = 2 [default = "abc"];
      case 2: {
        if (static_cast< ::google::protobuf::uint8>(tag) == (18 & 0xFF)) {
          DO_(::google::protobuf::internal::WireFormatLite::ReadString(
                input, this->mutable_str()));
          ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
            this->str().data(), static_cast<int>(this->str().length()),
            ::google::protobuf::internal::WireFormat::PARSE,
            "lm.hello.str");
        } else {
          goto handle_unusual;
        }
        break;
      }

      // optional int32 op = 3 [default = 15];
      case 3: {
        if (static_cast< ::google::protobuf::uint8>(tag) == (24 & 0xFF)) {
          HasBitSetters::set_has_op(this);
          DO_((::google::protobuf::internal::WireFormatLite::ReadPrimitive<
                   ::google::protobuf::int32, ::google::protobuf::internal::WireFormatLite::TYPE_INT32>(
                 input, &op_)));
        } else {
          goto handle_unusual;
        }
        break;
      }

      default: {
      handle_unusual:
        if (tag == 0) {
          goto success;
        }
        DO_(::google::protobuf::internal::WireFormat::SkipField(
              input, tag, _internal_metadata_.mutable_unknown_fields()));
        break;
      }
    }
  }
success:
  // @@protoc_insertion_point(parse_success:lm.hello)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:lm.hello)
  return false;
#undef DO_
}
#endif  // GOOGLE_PROTOBUF_ENABLE_EXPERIMENTAL_PARSER

void hello::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:lm.hello)
  ::google::protobuf::uint32 cached_has_bits = 0;
  (void) cached_has_bits;

  cached_has_bits = _has_bits_[0];
  // optional int32 id = 1;
  if (cached_has_bits & 0x00000002u) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(1, this->id(), output);
  }

  // required string str = 2 [default = "abc"];
  if (cached_has_bits & 0x00000001u) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->str().data(), static_cast<int>(this->str().length()),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "lm.hello.str");
    ::google::protobuf::internal::WireFormatLite::WriteStringMaybeAliased(
      2, this->str(), output);
  }

  // optional int32 op = 3 [default = 15];
  if (cached_has_bits & 0x00000004u) {
    ::google::protobuf::internal::WireFormatLite::WriteInt32(3, this->op(), output);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        _internal_metadata_.unknown_fields(), output);
  }
  // @@protoc_insertion_point(serialize_end:lm.hello)
}

::google::protobuf::uint8* hello::InternalSerializeWithCachedSizesToArray(
    ::google::protobuf::uint8* target) const {
  // @@protoc_insertion_point(serialize_to_array_start:lm.hello)
  ::google::protobuf::uint32 cached_has_bits = 0;
  (void) cached_has_bits;

  cached_has_bits = _has_bits_[0];
  // optional int32 id = 1;
  if (cached_has_bits & 0x00000002u) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(1, this->id(), target);
  }

  // required string str = 2 [default = "abc"];
  if (cached_has_bits & 0x00000001u) {
    ::google::protobuf::internal::WireFormat::VerifyUTF8StringNamedField(
      this->str().data(), static_cast<int>(this->str().length()),
      ::google::protobuf::internal::WireFormat::SERIALIZE,
      "lm.hello.str");
    target =
      ::google::protobuf::internal::WireFormatLite::WriteStringToArray(
        2, this->str(), target);
  }

  // optional int32 op = 3 [default = 15];
  if (cached_has_bits & 0x00000004u) {
    target = ::google::protobuf::internal::WireFormatLite::WriteInt32ToArray(3, this->op(), target);
  }

  if (_internal_metadata_.have_unknown_fields()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        _internal_metadata_.unknown_fields(), target);
  }
  // @@protoc_insertion_point(serialize_to_array_end:lm.hello)
  return target;
}

size_t hello::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:lm.hello)
  size_t total_size = 0;

  if (_internal_metadata_.have_unknown_fields()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        _internal_metadata_.unknown_fields());
  }
  // required string str = 2 [default = "abc"];
  if (has_str()) {
    total_size += 1 +
      ::google::protobuf::internal::WireFormatLite::StringSize(
        this->str());
  }
  ::google::protobuf::uint32 cached_has_bits = 0;
  // Prevent compiler warnings about cached_has_bits being unused
  (void) cached_has_bits;

  cached_has_bits = _has_bits_[0];
  if (cached_has_bits & 0x00000006u) {
    // optional int32 id = 1;
    if (cached_has_bits & 0x00000002u) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->id());
    }

    // optional int32 op = 3 [default = 15];
    if (cached_has_bits & 0x00000004u) {
      total_size += 1 +
        ::google::protobuf::internal::WireFormatLite::Int32Size(
          this->op());
    }

  }
  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  SetCachedSize(cached_size);
  return total_size;
}

void hello::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:lm.hello)
  GOOGLE_DCHECK_NE(&from, this);
  const hello* source =
      ::google::protobuf::DynamicCastToGenerated<hello>(
          &from);
  if (source == nullptr) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:lm.hello)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:lm.hello)
    MergeFrom(*source);
  }
}

void hello::MergeFrom(const hello& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:lm.hello)
  GOOGLE_DCHECK_NE(&from, this);
  _internal_metadata_.MergeFrom(from._internal_metadata_);
  ::google::protobuf::uint32 cached_has_bits = 0;
  (void) cached_has_bits;

  cached_has_bits = from._has_bits_[0];
  if (cached_has_bits & 0x00000007u) {
    if (cached_has_bits & 0x00000001u) {
      _has_bits_[0] |= 0x00000001u;
      str_.AssignWithDefault(&::lm::hello::_i_give_permission_to_break_this_code_default_str_.get(), from.str_);
    }
    if (cached_has_bits & 0x00000002u) {
      id_ = from.id_;
    }
    if (cached_has_bits & 0x00000004u) {
      op_ = from.op_;
    }
    _has_bits_[0] |= cached_has_bits;
  }
}

void hello::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:lm.hello)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void hello::CopyFrom(const hello& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:lm.hello)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool hello::IsInitialized() const {
  if ((_has_bits_[0] & 0x00000001) != 0x00000001) return false;
  return true;
}

void hello::Swap(hello* other) {
  if (other == this) return;
  InternalSwap(other);
}
void hello::InternalSwap(hello* other) {
  using std::swap;
  _internal_metadata_.Swap(&other->_internal_metadata_);
  swap(_has_bits_[0], other->_has_bits_[0]);
  str_.Swap(&other->str_, &::lm::hello::_i_give_permission_to_break_this_code_default_str_.get(),
    GetArenaNoVirtual());
  swap(id_, other->id_);
  swap(op_, other->op_);
}

::google::protobuf::Metadata hello::GetMetadata() const {
  ::google::protobuf::internal::AssignDescriptors(&::assign_descriptors_table_serialize_2eproto);
  return ::file_level_metadata_serialize_2eproto[kIndexInFileMessages];
}


// @@protoc_insertion_point(namespace_scope)
}  // namespace lm
namespace google {
namespace protobuf {
template<> PROTOBUF_NOINLINE ::lm::hello* Arena::CreateMaybeMessage< ::lm::hello >(Arena* arena) {
  return Arena::CreateInternal< ::lm::hello >(arena);
}
}  // namespace protobuf
}  // namespace google

// @@protoc_insertion_point(global_scope)
#include <google/protobuf/port_undef.inc>
