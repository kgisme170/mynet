﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebClient1.HelloService {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="HelloService.WebService1Soap")]
    public interface WebService1Soap {
        
        // CODEGEN: Generating message contract since element name name from namespace http://tempuri.org/ is not marked nillable
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/GetMessage", ReplyAction="*")]
        WebClient1.HelloService.GetMessageResponse GetMessage(WebClient1.HelloService.GetMessageRequest request);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/GetMessage", ReplyAction="*")]
        System.Threading.Tasks.Task<WebClient1.HelloService.GetMessageResponse> GetMessageAsync(WebClient1.HelloService.GetMessageRequest request);
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class GetMessageRequest {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Name="GetMessage", Namespace="http://tempuri.org/", Order=0)]
        public WebClient1.HelloService.GetMessageRequestBody Body;
        
        public GetMessageRequest() {
        }
        
        public GetMessageRequest(WebClient1.HelloService.GetMessageRequestBody Body) {
            this.Body = Body;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.Runtime.Serialization.DataContractAttribute(Namespace="http://tempuri.org/")]
    public partial class GetMessageRequestBody {
        
        [System.Runtime.Serialization.DataMemberAttribute(EmitDefaultValue=false, Order=0)]
        public string name;
        
        public GetMessageRequestBody() {
        }
        
        public GetMessageRequestBody(string name) {
            this.name = name;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class GetMessageResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Name="GetMessageResponse", Namespace="http://tempuri.org/", Order=0)]
        public WebClient1.HelloService.GetMessageResponseBody Body;
        
        public GetMessageResponse() {
        }
        
        public GetMessageResponse(WebClient1.HelloService.GetMessageResponseBody Body) {
            this.Body = Body;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
    [System.Runtime.Serialization.DataContractAttribute(Namespace="http://tempuri.org/")]
    public partial class GetMessageResponseBody {
        
        [System.Runtime.Serialization.DataMemberAttribute(EmitDefaultValue=false, Order=0)]
        public string GetMessageResult;
        
        public GetMessageResponseBody() {
        }
        
        public GetMessageResponseBody(string GetMessageResult) {
            this.GetMessageResult = GetMessageResult;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface WebService1SoapChannel : WebClient1.HelloService.WebService1Soap, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class WebService1SoapClient : System.ServiceModel.ClientBase<WebClient1.HelloService.WebService1Soap>, WebClient1.HelloService.WebService1Soap {
        
        public WebService1SoapClient() {
        }
        
        public WebService1SoapClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public WebService1SoapClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public WebService1SoapClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public WebService1SoapClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        WebClient1.HelloService.GetMessageResponse WebClient1.HelloService.WebService1Soap.GetMessage(WebClient1.HelloService.GetMessageRequest request) {
            return base.Channel.GetMessage(request);
        }
        
        public string GetMessage(string name) {
            WebClient1.HelloService.GetMessageRequest inValue = new WebClient1.HelloService.GetMessageRequest();
            inValue.Body = new WebClient1.HelloService.GetMessageRequestBody();
            inValue.Body.name = name;
            WebClient1.HelloService.GetMessageResponse retVal = ((WebClient1.HelloService.WebService1Soap)(this)).GetMessage(inValue);
            return retVal.Body.GetMessageResult;
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        System.Threading.Tasks.Task<WebClient1.HelloService.GetMessageResponse> WebClient1.HelloService.WebService1Soap.GetMessageAsync(WebClient1.HelloService.GetMessageRequest request) {
            return base.Channel.GetMessageAsync(request);
        }
        
        public System.Threading.Tasks.Task<WebClient1.HelloService.GetMessageResponse> GetMessageAsync(string name) {
            WebClient1.HelloService.GetMessageRequest inValue = new WebClient1.HelloService.GetMessageRequest();
            inValue.Body = new WebClient1.HelloService.GetMessageRequestBody();
            inValue.Body.name = name;
            return ((WebClient1.HelloService.WebService1Soap)(this)).GetMessageAsync(inValue);
        }
    }
}