# Mice and Cats in a Network Game  ![Javal](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
> Fortgeschrittene objektorientierte Programmierung 2024S
> 
> First Programming Task

## Foop V
>### &#127942; [_Dudina Anastasiia_](https://tuwel.tuwien.ac.at/user/view.php?id=182690&course=63218/)
>### &#127942; [_Peura Johan Niklas_](https://tuwel.tuwien.ac.at/user/view.php?id=78904&course=63218/)
>### &#127942; [_Ramírez Mejía Lorenzo_](https://tuwel.tuwien.ac.at/user/view.php?id=107014&course=63218/)

<details>

<summary>Description of the task</summary>
Design and implement a network-based game in an object-oriented
programming language of your choice (but not in a language or
framework specialized for game development). The game shall correspond to the following description.

A rectangular playing field contains several subways (not connected with each other) where mice are safe from being caught by
cats staying at the surface. Each subway has several exits to the
surface. Mice in subways can move without being seen by cats, and
cats can move on the surface without being seen by mice in subways. 

A mouse in a subway sees other mice in the same subway, but
not mice in other subways. Cats and mice on the surface see each
other, and cats try to catch mice that show up at the surface. Each
mouse knows all subways and their exits, but cats see only exits and
do not know how they are connected by subways. When a mouse
safely enters a subway, it informs other mice in this subway about
positions of cats at the time of entering. Initially, mice are located
in arbitrary (when possible different) subways and cats somewhere
on the surface. 

All mice want to meet in one subway, but it is not
predetermined in which one. Mice in the same subway can coordinate themselves by voting for a specific subway to meet, seeing the
votes of other mice in the same subway and adapting their votes until there is an agreement. But, mice in one subway cannot see votes
of mice in other subways. To distribute information and to meet,
some mice must move to other subways, but thereby they have to
cover a distance at the surface. Every stay at the surface is risky for
mice, the longer the more dangerous. A game ends when all surviving mice are in the same subway (in this case the surviving mice are
the winners) or after a predefined amount of time (no mouse wins
because the aim of meeting all others was not achieved).

Each player (real person) controls a mouse. Cats that try to
catch mice are controlled by computer algorithms (not necessarily
each cat by the same algorithm). To make the game more interesting, there can also be mice controlled by computer algorithms,
but that is not absolutely necessary. Each player uses his or her own
computer connected to a network. It must be possible that at least 4
players on 4 different computers participate in the same game. The
communication between the computers shall be efficient enough to
avoid noticeable delays for the largest possible playing field (with
reasonable resolution on the available screen size).

Please select appropriate details of the game by yourselves. Give
your fancy full scope.

Besides showing program code and giving information on the
technical realization of the game, please give a short presentation
how your program runs in a distributed environment and answer
the following questions in the Abgabegespräch:
+ Which number and forms of subways, number of exits, number and strategies of cats (as well as mice controlled by computer algorithms), playing time, mechanisms of controlling
mice, etc., provide the most exciting gaming experience? Which
variations did you try out?
+ Which playing strategy of mice is most promising (staying in
a subway as long as possible, moving to another subway early,
etc.)? Which strategies did you try out?

>Hint: Please don’t forget that implementing the game aims at
gaining experience. A functioning program is just one part of the
task. It is also important to look for proper program structures and
comments, a good strategy for the communication between computers, appealing gaming experiences and good answers to questions.

</details>
