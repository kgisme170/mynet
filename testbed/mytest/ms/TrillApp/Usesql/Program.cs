using System;
using System.Data.SqlClient;
using System.Diagnostics;

namespace Usesql
{
    class Program
    {
        static void Main(string[] args)
        {
            SqlConnection conn = new SqlConnection("Data Source=MININT-EP12N1V;Initial Catalog=EmployeeDB;Integrated Security=True");
            conn.Open();
            SqlCommand cmd = new SqlCommand(
                "select FirstName, LastName from Employees",
                conn
            );
            SqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                Console.Write("{1}, {0}", reader.GetString(0), reader.GetString(1));
            }
            reader.Close();
            cmd.Dispose();
            conn.Close();

            if (Debugger.IsAttached)
            {
                Console.ReadLine();
            }
        }
    }
}
