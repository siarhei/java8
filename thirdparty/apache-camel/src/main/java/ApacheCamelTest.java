import org.apache.camel.CamelContext;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;

/**
 * @author sshchahratsou
 */
public class ApacheCamelTest {
    static ProducerTemplate template;

    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(createNumbersRoute());
        context.addRoutes(createEvenNumbersRoute());
        context.addRoutes(createOddNumbersRoute());
        context.start();

        template = new DefaultProducerTemplate(context);
        template.start();

        for (int i = 0; i < 5; i++) {
            template.send("direct:numbers", ExchangePattern.InOnly, createNumbersRoute(i));
        }

        //Thread.sleep(5000);
        template.stop();
        context.stop();
    }

    private static Processor createNumbersRoute(int i) {
        return exchange -> exchange.getIn().setBody(i);
    }

    private static RouteBuilder createNumbersRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:numbers")
                        .process(exchange -> {
                            Object body = exchange.getIn().getBody();
                            System.out.println("InBody: " + body);

                            //if (!exchange.hasOut()) {
                            //exchange.setOut(new DefaultMessage(context));
                            //exchange.getOut().setBody("reply " + body);
                            //}
                            exchange.getIn().setHeader("even", Integer.parseInt(body.toString()) % 2 == 0);
                        })
                        .choice()
                        .when(simple("${header.even}"))
                        .to("direct:even-numbers")
                        .otherwise()
                        .to("direct:odd-numbers");
            }
        };
    }

    private static RouteBuilder createEvenNumbersRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:even-numbers")
                        .routeId("even-numbers")
                        .tracing()
                        .log(">>> ${body}")
                        .process(exchange -> {
                            Object body = exchange.getIn().getBody();
                            System.out.println("Even number processor. Body: " + body);
                        });
            }
        };
    }

    private static RouteBuilder createOddNumbersRoute() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:odd-numbers")
                        .process(exchange -> {
                            Object body = exchange.getIn().getBody();
                            System.out.println("Odd number processor. Body: " + body);
                        });
            }
        };
    }
}
