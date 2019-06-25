namespace TaskAsync
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.buttonSyncTask = new System.Windows.Forms.Button();
            this.buttonAsyncTask = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // buttonSyncTask
            // 
            this.buttonSyncTask.Location = new System.Drawing.Point(125, 12);
            this.buttonSyncTask.Name = "buttonSyncTask";
            this.buttonSyncTask.Size = new System.Drawing.Size(165, 55);
            this.buttonSyncTask.TabIndex = 0;
            this.buttonSyncTask.Text = "SyncTask";
            this.buttonSyncTask.UseVisualStyleBackColor = true;
            this.buttonSyncTask.Click += new System.EventHandler(this.buttonSyncTask_Click);
            // 
            // buttonAsyncTask
            // 
            this.buttonAsyncTask.Location = new System.Drawing.Point(125, 83);
            this.buttonAsyncTask.Name = "buttonAsyncTask";
            this.buttonAsyncTask.Size = new System.Drawing.Size(165, 57);
            this.buttonAsyncTask.TabIndex = 1;
            this.buttonAsyncTask.Text = "AsyncTask";
            this.buttonAsyncTask.UseVisualStyleBackColor = true;
            this.buttonAsyncTask.Click += new System.EventHandler(this.buttonAsyncTask_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(72, 180);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(303, 31);
            this.textBox1.TabIndex = 2;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(12F, 25F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(430, 245);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.buttonAsyncTask);
            this.Controls.Add(this.buttonSyncTask);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button buttonSyncTask;
        private System.Windows.Forms.Button buttonAsyncTask;
        private System.Windows.Forms.TextBox textBox1;
    }
}

