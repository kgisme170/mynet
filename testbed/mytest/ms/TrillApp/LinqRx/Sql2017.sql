DECLARE @num int;
SET @num=128;
SELECT 
@num AS 'localVariable',
@@SERVERNAME AS 'ServerName',
@@SERVERNAME AS 'ServiceName',
@@CONNECTIONS AS 'Connections',
@@CURSOR_ROWS AS 'CursorRows',
@@MAX_CONNECTIONS AS 'MaxConnections',
@@ROWCOUNT AS 'RowCount',
@@TRANCOUNT AS 'TranCount',
@@VERSION AS 'Version',
@@CPU_BUSY AS 'CpuBusy',
@@ERROR AS 'Error',
@@FETCH_STATUS AS 'FetchStatus',
@@PROCID AS 'Procid',
@@REMSERVER AS 'RemServer',
@@SPID AS 'Spid'