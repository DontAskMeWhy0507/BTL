# P-256 'Real' Mode (Non-Visual) Integration

## Overview

This implementation integrates P-256 elliptic curve cryptography into the Library Management System, supporting both visual (GUI) and real (non-visual/console) modes.

## Features

### P-256 Cryptographic Security
- **Elliptic Curve**: secp256r1 (P-256 NIST standard)
- **Key Generation**: 256-bit ECC key pairs
- **Hashing**: SHA-256 for password security
- **Digital Signatures**: SHA256withECDSA for authentication tokens

### Dual Mode Support
- **Visual Mode**: Original JavaFX GUI with P-256 enhanced authentication
- **Real Mode**: Console-based interface for headless/server environments

## Usage

### Real Mode (Non-Visual)
```bash
# Launch in console mode
java -cp "target/classes:sqlite-jdbc-3.45.1.0.jar" org.example.demo6.P256Launcher --real
java -cp "target/classes:sqlite-jdbc-3.45.1.0.jar" org.example.demo6.P256Launcher --headless
java -cp "target/classes:sqlite-jdbc-3.45.1.0.jar" org.example.demo6.P256Launcher --console

# Or using AppLaunch
java -cp "target/classes:sqlite-jdbc-3.45.1.0.jar" org.example.demo6.AppLaunch --real
```

### Visual Mode (GUI)
```bash
# Launch in GUI mode (default)
java -cp "target/classes:sqlite-jdbc-3.45.1.0.jar" org.example.demo6.P256Launcher
java -cp "target/classes:sqlite-jdbc-3.45.1.0.jar" org.example.demo6.AppLaunch
```

## Implementation Details

### Core Classes

#### P256Crypto.java
- Main cryptographic engine
- Handles key generation, signing, verification
- Mode-aware error handling (console vs GUI)
- Password hashing with SHA-256

#### ConsoleAuth.java
- Console-based authentication interface
- Real mode login/registration
- Database integration with P-256 security
- Secure password input handling

#### P256Launcher.java
- Mode detection and launching
- Command-line argument parsing
- Fallback from GUI to console mode

### Database Integration
- **Backward Compatibility**: Existing plain-text passwords still work
- **Enhanced Security**: New passwords use P256:hash format
- **Automatic Migration**: Passwords are upgraded on next login

### Security Features
- **Password Hashing**: SHA-256 instead of plain text storage
- **Digital Signatures**: Authentication tokens with ECC signatures
- **Key Management**: Secure key generation and storage
- **Mode Isolation**: Real mode operates without JavaFX dependencies

## Testing

Run P-256 specific tests:
```bash
mvn test -Dtest=P256Test
```

## Architecture Benefits

1. **Security**: Strong cryptographic foundation with P-256 ECC
2. **Flexibility**: Supports both GUI and headless deployments
3. **Compatibility**: Backward compatible with existing user accounts
4. **Minimal Changes**: Surgical integration without breaking existing functionality
5. **Standards Compliance**: Uses NIST P-256 curve and standard algorithms

## Use Cases

### Real Mode Applications
- Server deployments without display
- Automated systems and scripts
- SSH terminal access
- Docker containers
- CI/CD environments

### Visual Mode Applications
- Desktop application usage
- Administrative interfaces
- User-friendly registration/login
- Full library management features

## Technical Notes

- P-256 curve is industry standard for security and performance
- Real mode gracefully handles environments without JavaFX
- Console input uses System.console() for secure password entry when available
- Error handling is mode-appropriate (console vs GUI alerts)
- All cryptographic operations use Java's built-in security providers