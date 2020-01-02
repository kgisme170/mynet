using System;

namespace WebClient1
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            HelloService.WebService1SoapClient client = new HelloService.WebService1SoapClient();
            Label1.Text = client.GetMessage(TextBox1.Text);
        }
    }
}