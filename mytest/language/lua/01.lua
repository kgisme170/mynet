#!/usr/bin/env lua
-- 行注释
--[[块注释]]
--[[
块注释常见技巧：只要在块注释的前面加上“-”即可取消注释
--]]

b = 2 -- 全局变量 b
local a = 1 -- 局部变量 a

page = [=[
<html>
<head>
<title>An html page</title>
</head>
<body>
<a href="http://www.lua.org">Lua</a>
</body>
<html>
--[[
a = "one string"
b = string.gsub(a, "one", "another")
print("a =", a)
print("b =", b)
--]]
]=]

print("-- print page string -- ")
print(page)

local t = {}
t[0] = 0
t[1] = 1
t[2] = 2
t[4] = 4
t["x"] = "x"
t["y"] = "y"

print("------ ipairs ------")
for i,v in ipairs(t) do
    print(i,v)
end

print("------ pairs ------")
for k,v in pairs(t) do
    print(k,v)
end

function f(t)
    print(table.concat(t, ","))
end
f{1, 2, 3}

print "Hello World" --> print("Hello World")
print [=[a multi-line
message]=]
print(type{})

local t5 = {}
t5[1] = 1
t5[2] = 2
t5[3] = 3
t5[4] = 4
t5[7] = 7
t5[1000] = 1000
print("t5", #t5) --> 4