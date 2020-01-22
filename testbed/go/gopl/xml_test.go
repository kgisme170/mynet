package main

import (
	"encoding/xml"
	"fmt"
	"io/ioutil"
	"os"
	"testing"
)

type Server struct {
	XMLName    xml.Name `xml:"server"`
	ServerName string   `xml:"serverName"`
	ServerIP   string   `xml:"serverIP"`
}

type XMLServers struct {
	XMLName     xml.Name `xml:"servers"`
	Version     string   `xml:"version,attr"`
	Servers     []Server `xml:"server"`
	Description string   `xml:",innerxml"`
}

func TestXml(t *testing.T) {
	file, err := os.Open("my.xml")
	if err != nil {
		fmt.Println(err)
		return
	}
	defer file.Close()
	data, err := ioutil.ReadAll(file)
	if err != nil {
		fmt.Println(err)
		return
	}

	v := XMLServers{}
	err = xml.Unmarshal(data, &v)
	if err != nil {
		fmt.Println(err)
		return
	}

	fmt.Fprintln(os.Stdout, v)

	v2 := &XMLServers{
		Version: "1",
	}

	v2.Servers = append(v.Servers, Server{
		ServerName: "Shanghai_VPN",
		ServerIP:   "127.0.0.1",
	})

	v2.Servers = append(v.Servers, Server{
		ServerName: "Beijing_VPN",
		ServerIP:   "127.0.0.2",
	})

	output, err := xml.MarshalIndent(v2, "", "    ")
	if err != nil {
		fmt.Println(err)
		return
	}

	os.Stdout.Write([]byte(xml.Header))
	os.Stdout.Write(output)
}
