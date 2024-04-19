/*use master
EXEC SP_RENAMEDB Ucheb_0, Ucheb_5;
*/

use TestDatabase
exec sp_rename 'Client_alt.Surname', 'Sur'
