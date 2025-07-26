# MYVC Database System

A comprehensive relational database application developed for the Montréal Youth Volleyball Club (MYVC). This system models and manages detailed information about club members, family relationships, personnel, locations, team formations, training sessions, financial records, and automated communication workflows.

## Overview

This project simulates a real-world club management system through SQL-based data modeling and implementation. It supports complex constraints, historical data tracking, role-based access, and time-sensitive triggers and reports. The application is designed to ensure data consistency, enforce integrity rules, and automate weekly/monthly notifications.

## Key Features

- Entity-Relationship (ER) modeling and schema normalization (3NF and BCNF)
- Full SQL implementation including:
  - Table creation with integrity constraints (PK, FK, unique)
  - Data manipulation operations (INSERT, UPDATE, DELETE)
  - Advanced multi-table JOIN queries
  - Triggers for membership expiry and session validation
  - Email scheduling logic and logging
- Coverage of over 20 real-world queries and reports:
  - Personnel history and multi-location assignment
  - Member payments, status tracking, and role validation
  - Team formations with time conflict detection
  - Session-based role analysis and win/loss tracking
  - Member aging out and automated deactivation

## Tables and Core Entities

- `Locations` – Head and branch clubs with capacity and contact info
- `Personnel` – All employees including coaches, managers, volunteers
- `FamilyMembers` – Primary and secondary family contacts
- `ClubMembers` – Registered youth players aged 11–18
- `Memberships` – Annual status and payment tracking
- `Teams` – Team info including players and captains
- `TeamFormations` – Scheduled sessions with scores, player roles, constraints
- `Payments` – Membership payments and installment tracking
- `Logs` – Email generation logs (e.g., membership updates, session reminders)

## Triggers Implemented

- Automatic deactivation of members who turn 19
- Email generation for weekly sessions
- Time conflict rejection for overlapping team assignments

## Sample Queries Implemented

1. Display all active locations with full contact info
2. List all personnel with multi-role or multi-location history
3. Get club members with at least 3 location changes
4. List players who never missed a game or never lost a match
5. Show members with only specific roles (e.g., outside hitter only)
6. Report players who have covered **all 7 volleyball positions**
7. Identify inactive members due to unpaid fees or aging out
8. Generate game/training summaries by location/date
9. Find family members who are also team captains
10. Display full detail of a given family unit and associated players

## Technical Highlights

- All queries tested with ≥ 5 representative tuples
- Designed to work with any SQL-compliant RDBMS (e.g., PostgreSQL, MySQL)
- Ensures relational integrity and consistent referential logic
- Structured into modular scripts:
  - `ER_Design.pdf` – E/R Diagram and functional analysis
  - `Schema.sql` – CREATE TABLE and constraints
  - `InsertData.sql` – Sample data inserts
  - `Queries.sql` – Full query implementation
  - `Triggers.sql` – All trigger logic
  - `LogSamples.sql` – Email and audit log outputs

## UI & Demo

- A simple front-end interface was developed to demonstrate:
  - Location and member registration
  - Session scheduling and conflict detection
  - Payment tracking and team formation
- Screenshots included in `/ui_screens/`

## Project Scope

Designed as a full-cycle database solution covering:

- Conceptual Design → Relational Model → SQL Implementation
- Constraints, Triggers, Views, and Automation
- Real-world reporting and stakeholder simulation
