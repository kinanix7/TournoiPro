# TournoiPro - Docker Setup

## Quick Start

1. Build and run the application:
```bash
docker-compose up --build
```

2. Access the application at http://localhost:8080

## Services

- App: http://localhost:8080
- MySQL: localhost:3307 (changed from 3306 to avoid port conflicts)
- Database: tournoipro_db
- Database User: tournoiuser
- Database Password: tournoipassword

## Commands

- Start: `docker-compose up`
- Start in background: `docker-compose up -d`
- Stop: `docker-compose down`
- Rebuild: `docker-compose up --build`
- View logs: `docker-compose logs`
- View app logs: `docker-compose logs app`
- View db logs: `docker-compose logs db`

## Environment Variables

The application supports the following environment variables:

- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_JPA_HIBERNATE_DDL_AUTO`: Hibernate DDL auto setting

## Troubleshooting

If you encounter port conflicts:
1. Make sure no other MySQL instance is running on port 3306
2. The docker-compose.yml has been configured to use port 3307 to avoid conflicts
3. If port 3307 is also occupied, change it to another available port in docker-compose.yml

## Development

For development with hot reloading, create a docker-compose.override.yml file with volume mounts.