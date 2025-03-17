# Team Project Repository

## Repository Structure

```plaintext
project-repo/
│── marketing/                          # Our marketing team code
│   ├── src/
│   │   ├── features/                    # Our marketing-specific features
│   │   ├── implementations/              # Implementations of interfaces given to us
│   │   │   ├── from_operations/          # Implementation of Operations team’s interface
│   │   │   ├── from_box_office/          # Implementation of Box Office team’s interface
│   ├── tests/
│   ├── README.md
│── operations/                          # Operations team code (we don't edit this)
│   ├── src/
│   ├── tests/
│   ├── README.md
│── box-office/                          # Box Office team code (we don't edit this)
│   ├── src/
│   ├── tests/
│   ├── README.md
│── interfaces/                          # Interface definitions
│   ├── marketing_to_operations/         # Our interface for Operations team
│   ├── marketing_to_boxoffice/          # Our interface for Box Office team
│   ├── operations_to_marketing/         # The interface Operations gives us
│   ├── boxoffice_to_marketing/          # The interface Box Office gives us
│   ├── README.md
│── docs/                                # Documentation
│── README.md                            # General project overview
│── CONTRIBUTING.md                      # Contribution guidelines
