USE [master]
GO
/****** Object:  Database [newsfeed]    Script Date: 17/12/2014 13:21:24 ******/
CREATE DATABASE [newsfeed] ON  PRIMARY 
( NAME = N'newsfeed', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL10.MSSQLSERVER\MSSQL\DATA\newsfeed.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'newsfeed_log', FILENAME = N'c:\Program Files\Microsoft SQL Server\MSSQL10.MSSQLSERVER\MSSQL\DATA\newsfeed_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [newsfeed] SET COMPATIBILITY_LEVEL = 90
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [newsfeed].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [newsfeed] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [newsfeed] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [newsfeed] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [newsfeed] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [newsfeed] SET ARITHABORT OFF 
GO
ALTER DATABASE [newsfeed] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [newsfeed] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [newsfeed] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [newsfeed] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [newsfeed] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [newsfeed] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [newsfeed] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [newsfeed] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [newsfeed] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [newsfeed] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [newsfeed] SET  DISABLE_BROKER 
GO
ALTER DATABASE [newsfeed] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [newsfeed] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [newsfeed] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [newsfeed] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [newsfeed] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [newsfeed] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [newsfeed] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [newsfeed] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [newsfeed] SET  MULTI_USER 
GO
ALTER DATABASE [newsfeed] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [newsfeed] SET DB_CHAINING OFF 
GO
USE [newsfeed]
GO
/****** Object:  User [developer]    Script Date: 17/12/2014 13:21:24 ******/
CREATE USER [developer] FOR LOGIN [developer] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_owner] ADD MEMBER [developer]
GO
/****** Object:  StoredProcedure [dbo].[getCareersForUser]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[getCareersForUser]
	-- Add the parameters for the stored procedure here
	@user varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    declare @category varchar(50)

	select @category = category
	from users
	where id = @user

	if(@category = 'student')
		select * 
		from careers c
		where c.id in
		(
			select s.career 
			from subjects_students ss
			join subjects s
			on ss.subject = s.id
			where ss.student = @user
		)
	
	if(@category = 'professor')
		select * 
		from careers c
		where c.id in
		(
			select s.career from subjects s 
			where s.professor = @user
		)

	if(@category = 'director')
		select * 
		from careers c
		where c.director = @user
		
END

GO
/****** Object:  StoredProcedure [dbo].[getCommentsFeed]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[getCommentsFeed] 
	-- Add the parameters for the stored procedure here
	@size integer
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    select top 5 *
	from
	(
		select text, owner, ts from comments where deleted <> 1
		union
		select text, owner, ts from posts where deleted <> 1
	) t
	order by ts desc
END

GO
/****** Object:  StoredProcedure [dbo].[getCommentsForPost]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[getCommentsForPost]
	-- Add the parameters for the stored procedure here
	@post int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT *
	from comments
	where post = @post
	and deleted = 0
END

GO
/****** Object:  StoredProcedure [dbo].[getLikesForPost]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[getLikesForPost]
	-- Add the parameters for the stored procedure here
	@post int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * from likes
	where post = @post
END

GO
/****** Object:  StoredProcedure [dbo].[getNewsfeedsForUser]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[getNewsfeedsForUser]
	@user varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	declare @category varchar(50)

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
/****** Object:  StoredProcedure [dbo].[getPostsForNewsfeed]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[getPostsForNewsfeed]
	-- Add the parameters for the stored procedure here
	@newsfeed int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * from posts
	where newsfeed = @newsfeed
	and deleted = 0
END

GO
/****** Object:  Table [dbo].[careers]    Script Date: 17/12/2014 13:21:24 ******/
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
/****** Object:  Table [dbo].[comments]    Script Date: 17/12/2014 13:21:24 ******/
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
	[post] [int] NOT NULL,
	[enabled] [bit] NOT NULL CONSTRAINT [DF_comments_enabled]  DEFAULT ((1)),
	[deleted] [bit] NOT NULL CONSTRAINT [DF_comments_deleted]  DEFAULT ((0)),
	[ts] [varchar](50) NOT NULL CONSTRAINT [DF_comments_timestamp]  DEFAULT (getdate()),
	[attachment] [varchar](50) NULL,
 CONSTRAINT [PK_comments] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[likes]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[likes](
	[owner] [varchar](50) NOT NULL,
	[post] [int] NOT NULL,
	[id] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK_likes] PRIMARY KEY CLUSTERED 
(
	[owner] ASC,
	[post] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[newsfeeds]    Script Date: 17/12/2014 13:21:24 ******/
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
/****** Object:  Table [dbo].[persons]    Script Date: 17/12/2014 13:21:24 ******/
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
/****** Object:  Table [dbo].[posts]    Script Date: 17/12/2014 13:21:24 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[posts](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[text] [varchar](140) NOT NULL,
	[owner] [varchar](50) NOT NULL,
	[newsfeed] [int] NOT NULL,
	[likes] [smallint] NOT NULL CONSTRAINT [DF_posts_likes]  DEFAULT ((0)),
	[attachment] [varchar](50) NULL,
	[enabled] [bit] NOT NULL CONSTRAINT [DF_posts_enabled]  DEFAULT ((1)),
	[deleted] [bit] NOT NULL CONSTRAINT [DF_posts_deleted]  DEFAULT ((0)),
	[ts] [varchar](50) NOT NULL CONSTRAINT [DF_posts_timestamp]  DEFAULT (getdate()),
 CONSTRAINT [PK_posts] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[subjects]    Script Date: 17/12/2014 13:21:24 ******/
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
/****** Object:  Table [dbo].[subjects_students]    Script Date: 17/12/2014 13:21:24 ******/
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
/****** Object:  Table [dbo].[users]    Script Date: 17/12/2014 13:21:24 ******/
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
/****** Object:  Index [UK_likes]    Script Date: 17/12/2014 13:21:24 ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_likes] ON [dbo].[likes]
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
SET ANSI_PADDING ON

GO
/****** Object:  Index [UK_newsfeeds]    Script Date: 17/12/2014 13:21:24 ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK_newsfeeds] ON [dbo].[newsfeeds]
(
	[subject] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[comments]  WITH CHECK ADD  CONSTRAINT [FK_comments_posts] FOREIGN KEY([post])
REFERENCES [dbo].[posts] ([id])
GO
ALTER TABLE [dbo].[comments] CHECK CONSTRAINT [FK_comments_posts]
GO
ALTER TABLE [dbo].[comments]  WITH CHECK ADD  CONSTRAINT [FK_comments_users] FOREIGN KEY([owner])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[comments] CHECK CONSTRAINT [FK_comments_users]
GO
ALTER TABLE [dbo].[likes]  WITH CHECK ADD  CONSTRAINT [FK_likes_posts] FOREIGN KEY([post])
REFERENCES [dbo].[posts] ([id])
GO
ALTER TABLE [dbo].[likes] CHECK CONSTRAINT [FK_likes_posts]
GO
ALTER TABLE [dbo].[likes]  WITH CHECK ADD  CONSTRAINT [FK_likes_users] FOREIGN KEY([owner])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[likes] CHECK CONSTRAINT [FK_likes_users]
GO
ALTER TABLE [dbo].[newsfeeds]  WITH CHECK ADD  CONSTRAINT [FK_newsfeeds_users] FOREIGN KEY([owner])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[newsfeeds] CHECK CONSTRAINT [FK_newsfeeds_users]
GO
ALTER TABLE [dbo].[posts]  WITH CHECK ADD  CONSTRAINT [FK_posts_newsfeeds] FOREIGN KEY([newsfeed])
REFERENCES [dbo].[newsfeeds] ([id])
GO
ALTER TABLE [dbo].[posts] CHECK CONSTRAINT [FK_posts_newsfeeds]
GO
ALTER TABLE [dbo].[posts]  WITH CHECK ADD  CONSTRAINT [FK_posts_users] FOREIGN KEY([owner])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[posts] CHECK CONSTRAINT [FK_posts_users]
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
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [CK_users_category] CHECK  (([category]='professor' OR [category]='student' OR [category]='director'))
GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [CK_users_category]
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'users', @level2type=N'CONSTRAINT',@level2name=N'CK_users_category'
GO
USE [master]
GO
ALTER DATABASE [newsfeed] SET  READ_WRITE 
GO
