using System;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TaskAsync
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void buttonSyncTask_Click(object sender, EventArgs e)
        {
            Task<int> t = Task.Run(() =>
            {
                int results = 0;
                for (int i = 0; i < 1000; ++i)
                {
                    results += i * 2;
                    Console.WriteLine("result=" + results);
                }
                Thread.Sleep(3000);
                //Task.Delay(3000);
                return results;
            });
            textBox1.Text = "Sync finish" + t.Result.ToString();
        }

        private async void buttonAsyncTask_Click(object sender, EventArgs e)
        {
            Task<int> t = Task.Run(() =>
            {
                int results = 0;
                for (int i = 0; i < 1000; ++i)
                {
                    results += i * 2;
                    Console.WriteLine("result=" + results);
                }
                Thread.Sleep(3000);
                return results;
            });
            await Task.WhenAll(t);
            textBox1.Text = "Async finish" + t.Result.ToString();
        }
    }
}
