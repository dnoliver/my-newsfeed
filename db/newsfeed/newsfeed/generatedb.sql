use newsfeed
go

delete from subjects_students
delete from subjects
delete from careers
delete from persons

delete from comments
delete from likes
delete from posts
delete from newsfeeds
delete from users
go

INSERT INTO [dbo].[persons]
           ([id]
           ,[firstname]
           ,[lastname])
     VALUES
           ('dnoliver','Nicolas','Oliver'),
		   ('joanavarro','Joaquin','Navarro'),
		   ('oarteaga','Orlando','Arteaga'),
		   ('mblanco','Majo','Blanco'),
		   ('mpastarini','Mariela','Pastarini'),
		   ('cosimani','Cesar','Osimani'),
		   ('wgeremia','Waldo','Geremia'),
		   ('jespeche','Jose','Espeche')
GO

USE [newsfeed]
GO

INSERT INTO [dbo].[careers]
           ([id]
           ,[director])
     VALUES
           ('Informatica','wgeremia'),
		   ('Telecomunicaciones','jespeche')
GO

USE [newsfeed]
GO

INSERT INTO [dbo].[subjects]
           ([id]
           ,[professor]
           ,[career])
     VALUES
           ('Programacion','cosimani','Informatica'),
		   ('Concurrente','cosimani','Informatica'),
		   ('Distribuida','mpastarini','Informatica'),
		   ('Servicios','mpastarini','Informatica'),
		   ('Redes','mpastarini','Telecomunicaciones')
GO

USE [newsfeed]
GO

INSERT INTO [dbo].[subjects_students]
           ([subject]
           ,[student])
     VALUES
           ('Programacion','dnoliver'),
		   ('Redes','dnoliver'),
		   ('Programacion','joanavarro'),
		   ('Concurrente','dnoliver'),
		   ('Distribuida','joanavarro'),
		   ('Servicios','mblanco'),
		   ('Programacion','oarteaga'),
		   ('Redes','oarteaga')
GO


USE [newsfeed]
GO

INSERT INTO [dbo].[users]
           ([id]
           ,[password]
           ,[category])
select 
p.id as id,
p.id as password,
'student' as category
from persons p
GO

update users
set category = 'professor'
where id in
(
	select distinct s.professor from subjects s
)
go

update users
set category = 'director'
where id in
(
	select distinct c.director from careers c
)
go


USE [newsfeed]
GO

INSERT INTO [dbo].[newsfeeds]
           ([career]
           ,[subject]
           ,[owner]
           ,[enabled])
select
s.career as career,
s.id as subject,
s.professor as owner,
1 as enabled
from subjects s
GO














