name: ✨ Feature Request
description: Suggest a new safety feature
labels: [enhancement]

body:
  - type: markdown
    attributes:
      value: |
        Thanks for suggesting a feature! Help us make Safe-Her better.

  - type: textarea
    id: problem
    attributes:
      label: Problem Statement
      description: What safety problem does this solve?
      placeholder: |
        I want to be able to...
        Because...
      validations:
        required: true

  - type: textarea
    id: solution
    attributes:
      label: Proposed Solution
      description: How should this feature work?
      placeholder: |
        The feature should...
        Users would...
      validations:
        required: true

  - type: textarea
    id: alternatives
    attributes:
      label: Alternatives Considered
      description: What other approaches exist?
      placeholder: Other ideas...

  - type: textarea
    id: impact
    attributes:
      label: Impact & Benefits
      description: How would this help users?
      placeholder: |
        This would help women by...
        Benefits include...

  - type: textarea
    id: mockups
    attributes:
      label: Mockups/References
      description: UI mockups, screenshots, links to similar features
      placeholder: Attach images or links...
