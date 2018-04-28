#include<libxml/parser.h>
#include<libxml/tree.h>
#include<libxml/xmlmemory.h>
#include<libxml/xpath.h>
#include<libxml/xpathInternals.h>
#include<iostream>
using namespace std;
int main(int argc,char*argv[]){
    xmlInitParser();
    xmlDocPtr doc=/*xmlParseFile(argv[1]);*/xmlNewDoc(BAD_CAST"1.0");
    xmlNodePtr root=xmlNewNode(NULL,BAD_CAST"root");
    xmlDocSetRootElement(doc,root);
    xmlNewTextChild(root,NULL,BAD_CAST"n1",BAD_CAST"text01");
    xmlNewTextChild(root,NULL,BAD_CAST"n2",BAD_CAST"text02");
    xmlNewTextChild(root,NULL,BAD_CAST"n3",BAD_CAST"text03");
    xmlNodePtr n=xmlNewNode(NULL,BAD_CAST"n1");
    xmlAddChild(root,n);
    xmlNodePtr t=xmlNewText(BAD_CAST"text01");
    xmlAddChild(n,t);
    xmlNewProp(n,BAD_CAST"myprop",BAD_CAST"yes");
    xmlSaveFormatFileEnc("m.xml",doc,"UTF-8",1);
    xmlFreeDoc(doc);

    doc=xmlReadFile("m.xml","UTF-8",XML_PARSE_RECOVER);
    if(NULL==doc){
        return -1;
    }
    root=xmlDocGetRootElement(doc);
    if(xmlStrcmp(root->name, BAD_CAST"root")){
        return -1;
    }
    xmlNodePtr cur=root->xmlChildrenNode;
    cout<<"name="<<cur->name<<endl;
    xmlCleanupParser();
    return 0;
}
