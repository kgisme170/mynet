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
        public HtmlClient()
        {
            InitializeCallbacks();
        }

        #region Public API
        public string DownloadHtml(string address)
        {
            return DownloadHtml(new Uri(address));
        }

        string DownloadHtml(Uri address)
        {
            return InternalDownload(address);
        }
        #endregion

        private delegate void DownloadHtmlCallback(Uri address, AsyncOperation asyncOperation);
        public void DownloadHtmlAsync(Uri address)
        {
            AsyncOperation asyncOperation = AsyncOperationManager.CreateOperation(null);
            DownloadHtmlCallback callback = new DownloadHtmlCallback(InternalDownloadHtmlAsync);
            callback.BeginInvoke(address, asyncOperation, null, null);
        }

        public event EventHandler<DownloadHtmlCompletedEventArgs> DownloadHtmlCompleted;
        protected virtual void OnDownloadHtmlCompleted(DownloadHtmlCompletedEventArgs e)
        {
            DownloadHtmlCompleted?.Invoke(this, e);
        }
        private void InternalDownloadHtmlAsync(Uri address, AsyncOperation asyncOperation)
        {
            string html = InternalDownload(address);
            DownloadHtmlCompletedEventArgs e = new DownloadHtmlCompletedEventArgs(null, false, html);
            asyncOperation.PostOperationCompleted(DownloadHtmlCompletedCallback, e);
        }
        private void InitializeCallbacks()
        {
            DownloadHtmlCompletedCallback = new SendOrPostCallback(InternalDownloadHtmlCompleted);
        }
        private void InternalDownloadHtmlCompleted(object state)
        {
            DownloadHtmlCompletedEventArgs e = (DownloadHtmlCompletedEventArgs)state;
            OnDownloadHtmlCompleted(e);
        }
        private SendOrPostCallback DownloadHtmlCompletedCallback;
        public void DownloadHtmlAsync(string address)
        {
            DownloadHtmlAsync(new Uri(address));
        }
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
    }
}