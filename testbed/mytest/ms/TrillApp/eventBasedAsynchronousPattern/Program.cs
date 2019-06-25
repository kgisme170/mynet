using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace eventBasedAsynchronousPattern
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());

            //HtmlClient client = new HtmlClient();
            //string html = client.DownloadHtml("http://www.baidu.com");

            //client.DownloadHtmlCompleted += (sender, e) =>
            //{
            //    string html2 = e.Html;
            //};
            //client.DownloadHtmlAsync("http://www.baidu.com");
            //do
            //{
            //    Thread.Sleep(1);
            //} while (true);
        }
    }
}
