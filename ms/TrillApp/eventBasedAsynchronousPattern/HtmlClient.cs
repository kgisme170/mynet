using System;
using System.ComponentModel;
using System.IO;
using System.Net;
using System.Threading;

namespace eventBasedAsynchronousPattern
{
    /// <summary>
    /// Wraps an HttpWebRequest and returns a html
    /// </summary>
    [Description("Wraps an HttpWebRequest and returns a html")]
    public class HtmlClient : System.ComponentModel.Component
    {
        #region Sync Public API
        public string DownloadHtml(string address)
        {
            return DownloadHtml(new Uri(address));
        }

        string DownloadHtml(Uri address)
        {
            return InternalDownload(address);
        }
        #endregion
        #region download
        private string InternalDownload(Uri address)
        {
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(address);
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            string html = "";
            using (StreamReader reader = new StreamReader(response.GetResponseStream()))
            {
                html = reader.ReadToEnd();
            }
            Thread.Sleep(5000);
            return html;
        }
        #endregion
        public event EventHandler<DownloadHtmlCompletedEventArgs> DownloadHtmlCompleted; // 完成函数，事件处理的回调入口，由调用者赋值，这里体现event based
        private readonly SendOrPostCallback DownloadHtmlCompletedCallback;
        public HtmlClient() // 外部的EventHandler被注入到完成回调
        {
            DownloadHtmlCompletedCallback = new SendOrPostCallback((object state) => DownloadHtmlCompleted?.Invoke(this, (DownloadHtmlCompletedEventArgs)state));
        }

        private delegate void DownloadHtmlCallback(Uri address, AsyncOperation asyncOperation);
        public void DownloadHtmlAsync(Uri address) // 外部调用的入口
        {
            new DownloadHtmlCallback((Uri uri, AsyncOperation op) => // 下载函数
            {
                string html = InternalDownload(uri);
                DownloadHtmlCompletedEventArgs e = new DownloadHtmlCompletedEventArgs(null, false, html);
                op.PostOperationCompleted(DownloadHtmlCompletedCallback, e); // 下载完成后，调用complete函数，关联comple函数和e
            }).BeginInvoke(address, AsyncOperationManager.CreateOperation(null), null, null); // 调用async下载函数，关联async函数和和asyncOperation
        }
    }
}