using Moq;
using System;
using Xunit;

namespace XUnitCoreApp31
{
    public class UseMock1
    {
        public interface IWebService
        {
            void LogError(string msg);
        }

        public interface IEmailService
        {
            void SendEmail(string a, string b, string c, string d);
        }
        public class LogAnalyzer
        {
            public LogAnalyzer(IWebService serv, IEmailService mail)
            {
                Service = serv;
                Email = mail;
            }

            public IWebService Service { get; set; }

            public IEmailService Email { get; set; }

            public void Analyze(string fileName)
            {
                if (fileName.Length < 8)
                {
                    try
                    {
                        Service.LogError("the file name is to short" + fileName);
                    }
                    catch (Exception e)
                    {
                        Email.SendEmail("From@test.com", "To@test.com", "IWebServiceFailed", e.Message);
                    }
                }
            }
        }

        [Fact]
        public void AnalyzeTest()
        {
            var mockWebService = new Mock<IWebService>();
            mockWebService.Setup(p => p.LogError(It.Is<string>(str => str.Length > 8))).Throws(new Exception());
            var mockEmailService = new Mock<IEmailService>();
            var a = mockEmailService.Setup(e => e.SendEmail("From@test.com", "To@test.com", "IWebServiceFailed", It.Is<string>(x => x != null)));
            LogAnalyzer log = new LogAnalyzer(mockWebService.Object, mockEmailService.Object);

            log.Analyze("xxx");
            mockEmailService.Verify(p => p.SendEmail("From@test.com", "To@test.com", "IWebServiceFailed", It.Is<string>(x => x != null)));
        }
    }
}
