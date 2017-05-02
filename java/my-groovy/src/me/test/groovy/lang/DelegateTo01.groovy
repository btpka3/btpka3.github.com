package me.test.groovy.lang

import groovy.transform.TypeChecked

/**
 *
 */
class DelegateTo01 {

    @Delegate
    Date when
    String title

    static class BodySpec {
        void p(String msg) {
            println "Msg    : $msg"
        }


    }

    static class EmailSpec {
        void from(String from) {
            println "From   : $from"
        }

        void to(String... to) {
            println "To     : $to"
        }

        void subject(String subject) {
            println "Subject: $subject"
        }

        void body(@DelegatesTo(BodySpec) Closure body) {
            def bodySpec = new BodySpec()
            def code = body.rehydrate(bodySpec, this, this)
            code.resolveStrategy = Closure.DELEGATE_ONLY
            code()
        }
    }

    def email(@DelegatesTo(EmailSpec) Closure cl) {
        def email = new EmailSpec()
        def code = cl.rehydrate(email, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }

    @TypeChecked
    void sendEmail() {
        email {
            from 'dsl-guru@mycompany.com'
            to 'john.doe@waitaminute.com'
            subject 'The pope has resigned!'
            body {
                p 'Really, the pope has resigned!'
            }
        }
    }


    static void main(String[] args) {

        new DelegateTo01().sendEmail()

    }
}
