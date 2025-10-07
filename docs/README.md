# 🤖 DiHeng – Your Friendly Task Companion

Welcome to **DiHeng**, a playful and helpful chatbot designed to help you manage your tasks with ease while keeping the
experience fun and interactive!

DiHeng comes with a personality: expressive emojis, witty messages, and helpful guidance. Let’s make task management
enjoyable!

---

## 🌟 Features

- **Add Tasks**
    - `todo <description>` – Add a simple to-do item.
    - `deadline <description> /by <dd/MM/yyyy HH:mm>` – Add a task with a deadline.
        - Example: deadline finish project /by 01/12/2025 12:00
    - `event <description> /from <dd/MM/yyyy> /to <dd/MM/yyyy>` – Add an event with start and end times.
        - Example: event burger party /from 01/12/2025 /to 01/12/2025

- **View Tasks**
    - `list` – See all your tasks with current status and deadlines.

- **Update Task Status**
    - `mark <task index>` – Mark a task as done ✅.
    - `unmark <task index>` – Mark a task as not done 🔴.

- **Remove Tasks**
    - `delete <task index>` – Remove a task 🗑️.
    - `clear` – Remove all tasks and start fresh 🎉.

- **Load/Change Filepath**
    - `load <filepath>` – Change the location of your task storage file.

- **Help**
    - `help` – Displays a list of supported commands with guidance.

- **Exit**
    - `bye` – Quit DiHeng 👋.

---

## 🚀 Quick Start

1. **Java 17**
    - Ensure you have Java 17 or above installed on your computer
    - If not, download it [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

2. **JAR**
    - Download the latest release in [GitHub](https://github.com/grenn24/ip/releases/tag/A-UserGuide)
    - Go to the folder where the downloaded jar file is located
    - Run the jar file using the following command:
      ```java
      java -jar DiHeng.jar
      ```

3. **Start Adding Tasks**
    - Type commands in the input field, press **Enter** or click **Send**.

4. **Interact with DiHeng**
    - DiHeng will respond with fun emojis and helpful feedback.
    - Example:
      ```
      User: todo Buy groceries
      DiHeng: ⭐ Got it! Added this task: [T][ ] Buy groceries
      ```

5. **Enjoy Personality Features**
    - Emojis for task status: ✅, 🔴, 🗑️, 🎉
    - Witty, friendly messages for feedback and reminders

---

## 💡 Tips & Tricks

- **Multiple Task Operations:**
    - You can mark/unmark multiple tasks at once:
      ```
      mark 1 2 4
      ```
- **Keep Inputs Clean:**
    - Commands are case-insensitive but avoid unnecessary spaces.
- **Emojis for Fun:**
    - DiHeng uses emojis to give instant visual feedback. Don’t be surprised when it celebrates your progress 🎉.

---

## 🛠 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/diheng.git
