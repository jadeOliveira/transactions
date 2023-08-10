package br.com.jademe.transactions.business.service;

import br.com.jademe.transactions.business.dto.TimelineDTO;
import br.com.jademe.transactions.business.dto.TransactionDTO;
import br.com.jademe.transactions.business.entity.Transaction;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimelineService {

    @NonNull
    private final AccountService accountService;

    public static final String TABLE_NAME = "timeline";
    public static final String UUID_FIELD = "uuid";
    public static final String ACCOUNT_ID_FIELD = "accountId";
    public static final String DATA_FIELD = "data";

    public void saveToDynamo(TimelineDTO timelineDTO) {

        DynamoDbClient ddb = getDynamoClient();

        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put(UUID_FIELD, AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        itemValues.put(ACCOUNT_ID_FIELD, AttributeValue.builder().s(timelineDTO.getAccountId().toString()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(itemValues)
                .build();

        try {
            PutItemResponse response = ddb.putItem(request);
            log.info(TABLE_NAME + " was successfully updated. The request id is " + response.responseMetadata().requestId());
        } catch (ResourceNotFoundException e) {
            log.error("Error: The Amazon DynamoDB table \"%s\" can't be found.\n. System.err.println(\"\");", TABLE_NAME);
        } catch (DynamoDbException e) {
            log.error(e.getMessage());
        }
    }

    private DynamoDbClient getDynamoClient() {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();
        Region region = Region.SA_EAST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();

        return ddb;
    }

    private TransactionDTO entityToDTO(final Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .operationTypeId(transaction.getOperationType().getId())
                .amount(transaction.getAmount())
                .eventDate(transaction.getEventDate())
                .build();
    }

}
