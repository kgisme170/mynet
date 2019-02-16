-module(useless).
-export([add/2,greet/2]).
%% goes into this clause when both A and B are numbers
add(A, B) when is_number(A), is_number(B) ->
  A + B;
%% goes this clause when both A and B are lists
add(A, B) when is_list(A), is_list(B) ->
  A ++ B.

greet(Gender, Name) ->
  case Gender of
    male ->
      io:format("Hello, Mr. ~s!~n", [Name]);
    female ->
      io:format("Hello, Mrs. ~s!~n", [Name]);
    _ ->
      io:format("Hello, ~s!~n", [Name])
end.