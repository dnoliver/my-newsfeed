USE newsfeed
GO

USE [newsfeed]
GO
/****** Object:  User [developer]    Script Date: 24/10/2014 23:13:44 ******/
CREATE USER [developer] FOR LOGIN [developer] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [developer]
GO
/****** Object:  Table [dbo].[careers]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[careers](
	[id] [varchar](50) NOT NULL,
	[director] [varchar](50) NOT NULL,
 CONSTRAINT [PK_careers] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[comments]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[comments](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[text] [varchar](140) NOT NULL,
	[owner] [varchar](50) NOT NULL,
	[parent] [int] NULL,
	[likes] [smallint] NOT NULL CONSTRAINT [DF_comments_likes]  DEFAULT ((0)),
	[newsfeed] [int] NOT NULL,
 CONSTRAINT [PK_comments] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[newsfeeds]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[newsfeeds](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[career] [varchar](50) NOT NULL,
	[subject] [varchar](50) NOT NULL,
	[owner] [varchar](50) NOT NULL,
	[enabled] [bit] NOT NULL CONSTRAINT [DF_newsfeeds_enabled]  DEFAULT ((1)),
 CONSTRAINT [PK_newsfeeds] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[persons]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[persons](
	[id] [varchar](50) NOT NULL,
	[firstname] [varchar](50) NOT NULL,
	[lastname] [varchar](50) NOT NULL,
 CONSTRAINT [PK_persons] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sessions]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sessions](
	[id] [varchar](50) NOT NULL,
	[owner] [varchar](50) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[subjects]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[subjects](
	[id] [varchar](50) NOT NULL,
	[professor] [varchar](50) NOT NULL,
	[career] [varchar](50) NOT NULL,
 CONSTRAINT [PK_subjects] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[subjects_students]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[subjects_students](
	[subject] [varchar](50) NOT NULL,
	[student] [varchar](50) NOT NULL,
 CONSTRAINT [PK_subjects_students] PRIMARY KEY CLUSTERED 
(
	[subject] ASC,
	[student] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[users]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[users](
	[id] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL,
	[category] [varchar](50) NOT NULL,
 CONSTRAINT [PK_users] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [UK_users] UNIQUE NONCLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_newsfeeds]    Script Date: 24/10/2014 23:13:44 ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_newsfeeds] ON [dbo].[newsfeeds]
(
	[subject] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[comments]  WITH CHECK ADD  CONSTRAINT [FK_comments_comments] FOREIGN KEY([parent])
REFERENCES [dbo].[comments] ([id])
GO
ALTER TABLE [dbo].[comments] CHECK CONSTRAINT [FK_comments_comments]
GO
ALTER TABLE [dbo].[comments]  WITH CHECK ADD  CONSTRAINT [FK_comments_newsfeeds] FOREIGN KEY([newsfeed])
REFERENCES [dbo].[newsfeeds] ([id])
GO
ALTER TABLE [dbo].[comments] CHECK CONSTRAINT [FK_comments_newsfeeds]
GO
ALTER TABLE [dbo].[comments]  WITH CHECK ADD  CONSTRAINT [FK_comments_users] FOREIGN KEY([owner])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[comments] CHECK CONSTRAINT [FK_comments_users]
GO
ALTER TABLE [dbo].[newsfeeds]  WITH CHECK ADD  CONSTRAINT [FK_newsfeeds_users] FOREIGN KEY([owner])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[newsfeeds] CHECK CONSTRAINT [FK_newsfeeds_users]
GO
ALTER TABLE [dbo].[sessions]  WITH CHECK ADD  CONSTRAINT [FK__sessions_users] FOREIGN KEY([owner])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[sessions] CHECK CONSTRAINT [FK__sessions_users]
GO
ALTER TABLE [dbo].[subjects]  WITH CHECK ADD  CONSTRAINT [FK_subjects_careers] FOREIGN KEY([career])
REFERENCES [dbo].[careers] ([id])
GO
ALTER TABLE [dbo].[subjects] CHECK CONSTRAINT [FK_subjects_careers]
GO
ALTER TABLE [dbo].[subjects]  WITH CHECK ADD  CONSTRAINT [FK_subjects_persons] FOREIGN KEY([professor])
REFERENCES [dbo].[persons] ([id])
GO
ALTER TABLE [dbo].[subjects] CHECK CONSTRAINT [FK_subjects_persons]
GO
ALTER TABLE [dbo].[subjects_students]  WITH CHECK ADD  CONSTRAINT [FK_subjects_students_persons] FOREIGN KEY([student])
REFERENCES [dbo].[persons] ([id])
GO
ALTER TABLE [dbo].[subjects_students] CHECK CONSTRAINT [FK_subjects_students_persons]
GO
ALTER TABLE [dbo].[subjects_students]  WITH CHECK ADD  CONSTRAINT [FK_subjects_students_subjects] FOREIGN KEY([subject])
REFERENCES [dbo].[subjects] ([id])
GO
ALTER TABLE [dbo].[subjects_students] CHECK CONSTRAINT [FK_subjects_students_subjects]
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [CK_users_category] CHECK  (([category]='professor' OR [category]='student'))
GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [CK_users_category]
GO
/****** Object:  StoredProcedure [dbo].[getNewsfeedsForSession]    Script Date: 24/10/2014 23:13:44 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[getNewsfeedsForSession]
	@session varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	declare @user varchar(50)
	declare @category varchar(50)

	select @user = owner
	from sessions
	where id = @session

	select @category = category
	from users
	where id = @user

	if(@category = 'student')
		select * 
		from newsfeeds n
		where exists 
		(
			select * 
			from subjects_students s
			where s.student = @user
			and s.subject = n.subject
		)
		and n.enabled = 1
	
	if(@category = 'professor')
		select * 
		from newsfeeds n
		where n.owner = @user

	if(@category = 'director')
		select * 
		from newsfeeds n
		where exists
		(
			select * 
			from careers c
			where c.director = @user
			and c.id = n.career
		)
END

GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'users', @level2type=N'CONSTRAINT',@level2name=N'CK_users_category'
GO
USE [master]
GO
ALTER DATABASE [newsfeed] SET  READ_WRITE 
GO
