package main.model.controleurs.terminal;

import java.util.HashMap;

import main.model.dao.terminal.TerminalMessageDataManager;
import main.model.dto.terminal.TerminalMessage;

public class TerminalMessageControleur {
        HashMap<String, TerminalMessage> errorMessages;
        HashMap<String, TerminalMessage> warningMessages;
        HashMap<String, TerminalMessage> informationMessages;
        HashMap<String, TerminalMessage> donesMessages;
        HashMap<String, TerminalMessage> neutralMessages;

        public TerminalMessageControleur(String languagePreferences) {
                initWithProperties(languagePreferences);

                errorMessages = TerminalMessageDataManager.getAllErrorMessages();
                warningMessages = TerminalMessageDataManager.getAllWarningsMessages();
                informationMessages = TerminalMessageDataManager.getAllInformationMessages();
                neutralMessages = TerminalMessageDataManager.getAllNeutralsMessages();
                donesMessages = TerminalMessageDataManager.getAllDonesMessages();

        }

        public TerminalMessageControleur() {
                this("fr");
        }

        private void initWithProperties(String languagePreferences) {
                TerminalMessageDataManager.setLanguagePreferences(languagePreferences);

                TerminalMessageDataManager
                                .setSrcPath(TerminalMessageDataManager.TERMINALMESSAGE_PROPERTIES
                                                .getProperty("srcFolder"));

                TerminalMessageDataManager.setInformationsNameFile(
                                TerminalMessageDataManager.TERMINALMESSAGE_PROPERTIES
                                                .getProperty("informationsMessage"));
                TerminalMessageDataManager.setWarningsNameFile(
                                TerminalMessageDataManager.TERMINALMESSAGE_PROPERTIES.getProperty("warningsMessage"));
                TerminalMessageDataManager.setErrorsNameFile(
                                TerminalMessageDataManager.TERMINALMESSAGE_PROPERTIES.getProperty("errorsMessage"));
                TerminalMessageDataManager.setNeutralsNameFile(
                                TerminalMessageDataManager.TERMINALMESSAGE_PROPERTIES.getProperty("neutralMessage"));
                TerminalMessageDataManager.setDonesNameFile(
                                TerminalMessageDataManager.TERMINALMESSAGE_PROPERTIES.getProperty("donesMessage"));
        }

        public TerminalMessage get(String key) {
                TerminalMessage terminalMessage = neutralMessages.get(key) != null ? neutralMessages.get(key)
                                : warningMessages.get(key) != null ? warningMessages.get(key)
                                                : informationMessages.get(key) != null ? informationMessages.get(key)
                                                                : errorMessages.get(key) != null
                                                                                ? errorMessages.get(key)
                                                                                : donesMessages.get(key) != null
                                                                                                ? donesMessages.get(key)
                                                                                                : null;

                return terminalMessage;
        }

        public String getContent(String key) {
                return get(key).getContent();
        }

        public void show(String key) {
                get(key).show();
        }
}
