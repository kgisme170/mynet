#!/usr/bin/env python
# -*- coding: utf-8 -*-
import win32com.client as win32
from Tkinter import Tk
from tkMessageBox import showwarning
from time import sleep
warn=lambda app:showwarning(app, 'Exit?')
RANGE=range(3,8)
def excel():
    app='Excel'
    xl=win32.gencache.EnsureDispatch('%s.Application' % app)
    ss=xl.Workbooks.Add()
    sh=ss.ActiveSheet
    xl.Visible=True
    sleep(1)
    sh.Cells(1,1).Value='Python-to-%s Demo' % app
    sleep(1)
    for i in RANGE:
        sh.Cells(i,1).Value='Line %d' % i
        sleep(1)
        sh.Cells(i+2,i).Value="Th-th-th-that's all floks!"
    warn(app)
    ss.Close(False)
    xl.Application.Quit()
Tk().withdraw()
excel()