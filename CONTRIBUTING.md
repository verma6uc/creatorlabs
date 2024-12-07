# Contributing to CreatorLabs

Thank you for your interest in contributing to CreatorLabs! This document provides guidelines and instructions for contributing.

## Code of Conduct

By participating in this project, you agree to abide by our Code of Conduct:

1. Be respectful and inclusive
2. Exercise consideration and empathy
3. Focus on what is best for the community
4. Give and gracefully accept constructive feedback

## How to Contribute

### Reporting Bugs

1. Check if the bug has already been reported in the Issues section
2. If not, create a new issue with:
   - Clear title and description
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots if applicable
   - Environment details

### Suggesting Enhancements

1. Check existing issues and discussions
2. Create a new issue with:
   - Clear description of the enhancement
   - Rationale and use cases
   - Implementation suggestions if any
   - Mock-ups or examples if applicable

### Pull Requests

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write or update tests
5. Submit a pull request

## Development Setup

### Prerequisites

- JDK 22
- Maven 3.8+
- PostgreSQL 14+
- Git

### Local Development

1. Clone your fork:
```bash
git clone https://github.com/your-username/creatorlabs.git
cd creatorlabs
```

2. Set up database:
```sql
CREATE DATABASE creatorlabs;
```

3. Configure application.properties:
```properties
db.url=jdbc:postgresql://localhost:5432/creatorlabs
db.username=your_username
db.password=your_password
```

4. Build and run:
```bash
mvn clean install
mvn tomcat7:run
```

## Coding Standards

### Java Code Style

1. Follow Java naming conventions:
   - CamelCase for class names
   - camelCase for method and variable names
   - UPPER_CASE for constants

2. Documentation:
   - JavaDoc for public methods and classes
   - Inline comments for complex logic
   - Clear commit messages

3. Code Organization:
   - One class per file
   - Logical package structure
   - Clear separation of concerns

### JavaScript Code Style

1. Use modern ES6+ features
2. Follow consistent naming conventions
3. Document complex functions
4. Use proper error handling

### CSS Style

1. Use BEM naming convention
2. Organize by component
3. Use variables for consistency
4. Follow responsive design principles

## Testing

### Unit Tests

1. Write tests for new features
2. Update tests for changes
3. Maintain test coverage
4. Run tests before submitting:
```bash
mvn test
```

### Integration Tests

1. Test component interactions
2. Verify database operations
3. Check API endpoints
4. Test WebSocket functionality

## Pull Request Process

1. Update documentation
2. Add/update tests
3. Follow code style guidelines
4. Ensure CI passes
5. Request review from maintainers

### PR Checklist

- [ ] Code follows style guidelines
- [ ] Tests added/updated
- [ ] Documentation updated
- [ ] Changelog updated
- [ ] CI checks pass
- [ ] No merge conflicts

## Review Process

1. Maintainers will review PRs
2. Address feedback promptly
3. Keep discussions constructive
4. Be patient and respectful

## Release Process

1. Version numbers follow SemVer
2. Update CHANGELOG.md
3. Tag releases
4. Create release notes

## Additional Resources

- [README.md](README.md)
- [CHANGELOG.md](CHANGELOG.md)
- [LICENSE](LICENSE)

## Getting Help

1. Check documentation
2. Search existing issues
3. Ask in discussions
4. Contact maintainers

## Recognition

Contributors will be:
1. Listed in CONTRIBUTORS.md
2. Mentioned in release notes
3. Thanked in documentation

Thank you for contributing to CreatorLabs!
