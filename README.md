# DATAWAREHOUSE | Data Extraction and Transformation Application

## Description
This Java-based web application, built using the Vaadin framework, facilitates the extraction of data from various sources (SQL Server, Excel, CSV), combines them, performs transformations, and uploads the transformed data to Azure Cloud.

## Features
1. Extraction from SQL Server: Users can input SQL Server credentials and table name to extract data.
2. Excel and CSV File Import: Users can upload Excel and CSV files to extract data.
3. Transformation: After extraction, users can view the raw table data and apply transformations.
4. Azure Cloud Integration: Users can input Azure SQL Server credentials and table name to upload the transformed data.
5. Multi-step Workflow: The application guides users through each step of the process with intuitive UI elements.

## Instructions
1. Extraction Page:
   * Provide SQL Server information: Database name, username, password, and table name.
   * Select Excel or CSV file using the file dialog.
   * Click on the "Extract" button to proceed.
![extract](https://github.com/GoldenDovah/DataWarehouseInterface/assets/19519174/733c94dc-7446-4bb2-bcd2-088d974e589e)
2. Transformation Page:
   * View the extracted table data before transformation.
   * Click on the "Transform Data" button to apply transformations.
![before transform](https://github.com/GoldenDovah/DataWarehouseInterface/assets/19519174/ee3f0c83-d842-4166-8d4c-8f77c3d05bab)
   * Once transformed, click on the "Upload to Azure" button to proceed.
![after transform](https://github.com/GoldenDovah/DataWarehouseInterface/assets/19519174/d740317c-a820-495c-9f7d-be2959e404b7)
3. Azure Upload Page:
   * Enter Azure SQL Server information: Server name, database name, username, password, and new table name.
   * Click on the "Connect" button to establish a connection.
![before upload](https://github.com/GoldenDovah/DataWarehouseInterface/assets/19519174/a2aab3e4-f018-4c05-9c60-61bade2c5870)
   * After successful connection, click on the "Upload" button to upload the transformed data.
![After upload](https://github.com/GoldenDovah/DataWarehouseInterface/assets/19519174/6c7547f9-3d72-4f8e-8152-40bc401cac55)
