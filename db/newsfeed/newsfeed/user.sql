use newsfeed

CREATE LOGIN developer 
    WITH PASSWORD = 'intel123!';
GO

CREATE USER developer FOR LOGIN developer;
GO