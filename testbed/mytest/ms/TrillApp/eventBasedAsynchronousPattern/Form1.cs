using System;
using System.Windows.Forms;

namespace eventBasedAsynchronousPattern
{
    public partial class Form1 : Form
    {
        /// <summary>
        /// 
        /// </summary>
        public Form1()
        {
            InitializeComponent();
        }

        private void OnDownloadHtmlCompleted(object sender, DownloadHtmlCompletedEventArgs e)
        {
            string html = e.Html;
            progressBar1.Value = progressBar1.Maximum;
            progressBar1.Style = ProgressBarStyle.Blocks;
            button1.Enabled = true;
            button2.Enabled = true;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            button1.Enabled = false;
            button2.Enabled = false;

            progressBar1.Value = progressBar1.Minimum;
            progressBar1.Style = ProgressBarStyle.Marquee;
            string html = htmlClient1.DownloadHtml("http://www.baidu.com");
            progressBar1.Value = progressBar1.Maximum;
            progressBar1.Style = ProgressBarStyle.Blocks;
            button1.Enabled = true;
            button2.Enabled = true;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            button1.Enabled = false;
            button2.Enabled = false;

            progressBar1.Value = progressBar1.Minimum;
            progressBar1.Style = ProgressBarStyle.Marquee;
            htmlClient1.DownloadHtmlAsync(new Uri("http://www.baidu.com"));
        }
    }
}
