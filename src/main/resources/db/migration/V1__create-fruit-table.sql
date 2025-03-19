CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);
CREATE TABLE fruit (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    classificacao VARCHAR(20) NOT NULL,
    fresca BOOLEAN NOT NULL,
    quantidade_disponivel INT NOT NULL,
    valor_venda FLOAT NOT NULL,
    img_url VARCHAR(255)
);