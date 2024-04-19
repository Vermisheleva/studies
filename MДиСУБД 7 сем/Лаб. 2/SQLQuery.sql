--==========================================================================
-- Add column template
--
-- This template creates a table, then it adds a new column to the table.
--==========================================================================
USE TestDatabase

-- Add a new column to the table
ALTER TABLE dbo.Client_alt
	ADD Phone VARCHAR(20) not NULL
