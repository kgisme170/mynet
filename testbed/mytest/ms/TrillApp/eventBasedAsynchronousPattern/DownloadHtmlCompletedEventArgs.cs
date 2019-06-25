using System;

namespace eventBasedAsynchronousPattern
{
    /// <summary>
    /// Event arguments for HtmlClient.DownloadHtmlAsync
    /// </summary>
    public class DownloadHtmlCompletedEventArgs:System.ComponentModel.AsyncCompletedEventArgs
    {
        /// <summary>
        /// Instantiates a new instance of DownloadHtmlCompletedEventArgs class
        /// </summary>
        /// <param name="error"></param>
        /// <param name="cancelled"></param>
        /// <param name="html"></param>
        public DownloadHtmlCompletedEventArgs(Exception error, bool cancelled, string html):
            base(error, cancelled, null)
        {
            Html = html;
        }

        public string Html
        {
            get;
            private set;
        }
    }
}
